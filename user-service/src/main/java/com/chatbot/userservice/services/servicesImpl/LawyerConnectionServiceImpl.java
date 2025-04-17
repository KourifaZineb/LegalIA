package com.chatbot.userservice.services.servicesImpl;

import com.chatbot.userservice.entities.Lawyer;
import com.chatbot.userservice.entities.LawyerConnections;
import com.chatbot.userservice.entities.User;
import com.chatbot.userservice.entities.enums.ConnectionStatus;
import com.chatbot.userservice.mappers.LawyerConnectionsMapper;
import com.chatbot.userservice.repository.LawyerConnectionRepository;
import com.chatbot.userservice.repository.LawyerRepository;
import com.chatbot.userservice.repository.UserRepository;
import com.chatbot.userservice.services.LawyerConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LawyerConnectionServiceImpl implements LawyerConnectionService {

    private final LawyerConnectionRepository connectionRepository;
    private final UserRepository userRepository;
    private final LawyerRepository lawyerRepository;
    private final LawyerConnectionsMapper lawyerConnectionsMapper;


    @Override
    public LawyerConnections createConnection(LawyerConnections connection) {
        Long userId = connection.getUser().getUserId();
        Long lawyerId = connection.getLawyer().getLawyerId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Lawyer lawyer = lawyerRepository.findById(lawyerId)
                .orElseThrow(() -> new RuntimeException("Lawyer not found"));

        connection.setUser(user);
        connection.setLawyer(lawyer);
        connection.setRequestDate(LocalDateTime.now());
        connection.setStatus(ConnectionStatus.EN_ATTENTE);

        return connectionRepository.save(connection);
    }


    @Override
    public Optional<LawyerConnections> getConnectionById(Long id) {
        return connectionRepository.findById(id);
    }

    @Override
    public List<LawyerConnections> getConnectionsByUserId(Long userId) {
        return connectionRepository.findByUser_UserId(userId);
    }

    @Override
    public List<LawyerConnections> getConnectionsByLawyerId(Long lawyerId) {
        return connectionRepository.findByLawyer_LawyerId(lawyerId);
    }

    @Override
    public List<LawyerConnections> getConnectionsByStatus(String status) {
        ConnectionStatus parsedStatus;
        try {
            parsedStatus = ConnectionStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid connection status: " + status);
        }
        return connectionRepository.findByStatus(parsedStatus);
    }

    @Override
    public LawyerConnections updateConnection(LawyerConnections connection) {
        if (connection.getConnectionId() == null || !connectionRepository.existsById(connection.getConnectionId())) {
            throw new RuntimeException("Connection not found for update.");
        }
        return connectionRepository.save(connection);
    }

    @Override
    public void deleteConnection(Long id) {
        connectionRepository.deleteById(id);
    }

    @Override
    public void acceptConnection(Long connectionId) {
        updateConnectionStatus(connectionId, ConnectionStatus.ACCEPTÉ);
    }

    @Override
    public void rejectConnection(Long connectionId) {
        updateConnectionStatus(connectionId, ConnectionStatus.REJETÉ);
    }

    private void updateConnectionStatus(Long connectionId, ConnectionStatus status) {
        LawyerConnections connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new RuntimeException("Connection not found"));
        connection.setStatus(status);
        connectionRepository.save(connection);
    }
}
