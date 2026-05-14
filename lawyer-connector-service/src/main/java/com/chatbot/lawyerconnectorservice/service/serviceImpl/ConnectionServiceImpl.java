package com.chatbot.lawyerconnectorservice.service.serviceImpl;

import com.chatbot.commonlibrary.dtos.LawyerConnectionDTO;
import com.chatbot.commonlibrary.dtos.LawyerDTO;
import com.chatbot.commonlibrary.dtos.UserDTO;
import com.chatbot.commonlibrary.enums.ConnectionStatus;
import com.chatbot.commonlibrary.enums.LawyerStatus;
import com.chatbot.lawyerconnectorservice.client.LawyerFeignClient;
import com.chatbot.lawyerconnectorservice.client.UserFeignClient;
import com.chatbot.lawyerconnectorservice.model.Connection;
import com.chatbot.lawyerconnectorservice.repository.ConnectionRepository;
import com.chatbot.lawyerconnectorservice.service.ConnectionService;
import com.chatbot.lawyerconnectorservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final UserFeignClient userFeignClient;
    private final LawyerFeignClient lawyerFeignClient;
    private final NotificationService notificationService;
    private final HttpServletRequest request;

    @Override
    public LawyerConnectionDTO createConnection(LawyerConnectionDTO dto) {
        log.info("Creating new connection request from user {} to lawyer {}", dto.getUserId(), dto.getLawyerId());

        // Check if there's already a pending connection
        connectionRepository.findByUserIdAndLawyerIdAndStatus(
                dto.getUserId(), dto.getLawyerId(), ConnectionStatus.PENDING
        ).ifPresent(existing -> {
            throw new RuntimeException("A pending connection request already exists");
        });

        // Verify lawyer is available by checking their status
        LawyerDTO lawyer = lawyerFeignClient.getLawyerById(dto.getLawyerId());
        if (lawyer == null || lawyer.getStatus() != LawyerStatus.AVAILABLE) {
            throw new RuntimeException("Lawyer is not available for new connections");
        }

        // Create Connection entity from DTO
        Connection connection = new Connection();
        connection.setUserId(dto.getUserId());
        connection.setLawyerId(dto.getLawyerId());
        connection.setStatus(ConnectionStatus.PENDING);
        connection.setRequestDate(Instant.now());
        connection.setCaseDescription(dto.getCaseDescription());
        connection.setObjet(dto.getObjet());
        connection.setMessage(dto.getMessage());

        Connection savedConnection = connectionRepository.save(connection);

        // Send notification to lawyer and handle response
        CompletableFuture<Boolean> notificationResult = notificationService.notifyLawyerNewRequest(savedConnection);

        // You can choose to wait for the notification result or handle it asynchronously
        notificationResult.whenComplete((success, throwable) -> {
            if (success) {
                log.info("Notification successfully sent for connection {}", savedConnection.getId());
            } else {
                log.warn("Failed to send notification for connection {}", savedConnection.getId());
                // You could implement retry logic here or store the failure status
            }
        });

        LawyerConnectionDTO result = enrichConnectionDTO(savedConnection);

        // Optionally, you can add notification status to the DTO
        try {
            // Wait for notification result with timeout (e.g., 5 seconds)
            boolean notificationSent = notificationResult.get(5, java.util.concurrent.TimeUnit.SECONDS);
            // You could add a field to LawyerConnectionDTO to indicate notification status
            log.info("Connection {} created, notification sent: {}", savedConnection.getId(), notificationSent);
        } catch (Exception e) {
            log.warn("Timeout or error waiting for notification result: {}", e.getMessage());
        }

        return result;
    }
    @Override
    @Transactional(readOnly = true)
    public LawyerConnectionDTO getConnectionById(Long id) {
        Connection connection = connectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Connection not found with id: " + id));

        return enrichConnectionDTO(connection);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LawyerConnectionDTO> getConnectionsByUserId(String userId) { // Changed from Long to String
        log.info("Fetching connections for user ID: {} (type: {})", userId, userId.getClass().getSimpleName());
        List<Connection> connections = connectionRepository.findByUserIdOrderByRequestDateDesc(userId);
        return connections.stream()
                .map(this::enrichConnectionDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LawyerConnectionDTO> getConnectionsByLawyerId(Long lawyerId) {
        List<Connection> connections = connectionRepository.findByLawyerIdOrderByRequestDateDesc(lawyerId);
        return connections.stream()
                .map(this::enrichConnectionDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LawyerConnectionDTO> getConnectionsByStatus(ConnectionStatus status) {
        List<Connection> connections = connectionRepository.findByStatusOrderByRequestDateDesc(status);
        return connections.stream()
                .map(this::enrichConnectionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LawyerConnectionDTO updateConnection(Long id, LawyerConnectionDTO dto) {
        Connection existingConnection = connectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Connection not found with id: " + id));

        // Update allowed fields
        if (dto.getCaseDescription() != null) {
            existingConnection.setCaseDescription(dto.getCaseDescription());
        }
        if (dto.getObjet() != null) {
            existingConnection.setObjet(dto.getObjet());
        }
        if (dto.getMessage() != null) {
            existingConnection.setMessage(dto.getMessage());
        }

        Connection updatedConnection = connectionRepository.save(existingConnection);
        return enrichConnectionDTO(updatedConnection);
    }

    @Override
    public void deleteConnection(Long id) {
        Connection connection = connectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Connection not found with id: " + id));

        connectionRepository.delete(connection);
        log.info("Connection {} deleted successfully", id);
    }

    @Override
    public LawyerConnectionDTO acceptConnection(Long connectionId) {
        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new RuntimeException("Connection not found with id: " + connectionId));

        if (connection.getStatus() != ConnectionStatus.PENDING) {
            throw new RuntimeException("Only pending connections can be accepted");
        }

        connection.setStatus(ConnectionStatus.ACCEPTED);
        connection.setResponseDate(Instant.now());

        Connection savedConnection = connectionRepository.save(connection);

        // Send notification to user
        notificationService.notifyUserConnectionAccepted(savedConnection);

        return enrichConnectionDTO(savedConnection);
    }

    @Override
    public LawyerConnectionDTO rejectConnection(Long connectionId) {
        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new RuntimeException("Connection not found with id: " + connectionId));

        if (connection.getStatus() != ConnectionStatus.PENDING) {
            throw new RuntimeException("Only pending connections can be rejected");
        }

        connection.setStatus(ConnectionStatus.REJECTED);
        connection.setResponseDate(Instant.now());

        Connection savedConnection = connectionRepository.save(connection);

        // Send notification to user
        notificationService.notifyUserConnectionRejected(savedConnection);

        return enrichConnectionDTO(savedConnection);
    }

    @Override
    public void cancelConnection(Long id) {
        Connection connection = connectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Connection not found with id: " + id));

        if (connection.getStatus() != ConnectionStatus.PENDING) {
            throw new RuntimeException("Only pending connections can be cancelled");
        }

        connection.setStatus(ConnectionStatus.CANCELLED);
        connection.setResponseDate(Instant.now());

        connectionRepository.save(connection);
        log.info("Connection {} cancelled successfully", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LawyerConnectionDTO> getPendingConnectionsForLawyer(Long lawyerId) {
        List<Connection> connections = connectionRepository
                .findByLawyerIdAndStatusOrderByRequestDateDesc(lawyerId, ConnectionStatus.PENDING);

        return connections.stream()
                .map(this::enrichConnectionDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LawyerConnectionDTO> getConnectionsInPeriod(Instant startDate, Instant endDate) {
        List<Connection> connections = connectionRepository.findConnectionsInPeriod(startDate, endDate);
        return connections.stream()
                .map(this::enrichConnectionDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Long getConnectionCountByStatus(Long lawyerId, ConnectionStatus status) {
        return connectionRepository.countByLawyerIdAndStatus(lawyerId, status);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getConnectionCountByUserStatus(String userId, ConnectionStatus status) { // New method for user UUID
        return connectionRepository.countByUserIdAndStatus(userId, status);
    }

    /**
     * Get all available lawyers using the status-based approach
     */
    @Transactional(readOnly = true)
    public List<LawyerDTO> getAvailableLawyers() {
        return lawyerFeignClient.getLawyersByStatus(LawyerStatus.AVAILABLE);
    }

    private LawyerConnectionDTO enrichConnectionDTO(Connection connection) {
        LawyerConnectionDTO dto = new LawyerConnectionDTO();
        dto.setId(connection.getId());
        dto.setUserId(connection.getUserId()); // Now String UUID
        dto.setLawyerId(connection.getLawyerId());
        dto.setStatus(connection.getStatus());
        dto.setCaseDescription(connection.getCaseDescription());
        dto.setObjet(connection.getObjet());
        dto.setMessage(connection.getMessage());
        dto.setRequestDate(connection.getRequestDate());
        dto.setResponseDate(connection.getResponseDate());

        // Fetch user info using UUID
        try {
            String authorizationHeader = getAuthorizationHeader();
            UserDTO userInfo = userFeignClient.getUserById(connection.getUserId(), authorizationHeader); // Pass UUID string with token
            dto.setUserInfo(userInfo);

            // Fetch lawyer info
            LawyerDTO lawyerInfo = lawyerFeignClient.getLawyerById(connection.getLawyerId());
            dto.setLawyerInfo(lawyerInfo);
        } catch (Exception e) {
            log.warn("Failed to enrich connection DTO for user {} and lawyer {}: {}",
                    connection.getUserId(), connection.getLawyerId(), e.getMessage());
        }

        return dto;
    }

    private String getAuthorizationHeader() {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("No valid authorization token found in request");
        }
        return authorizationHeader;
    }
}