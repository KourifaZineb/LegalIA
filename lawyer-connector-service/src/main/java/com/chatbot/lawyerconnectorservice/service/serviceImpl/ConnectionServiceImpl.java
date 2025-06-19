package com.chatbot.lawyerconnectorservice.service.serviceImpl;

import com.chatbot.commonlibrary.dtos.LawyerConnectionDTO;
import com.chatbot.commonlibrary.dtos.LawyerDTO;
import com.chatbot.commonlibrary.dtos.NotificationDTO;
import com.chatbot.commonlibrary.dtos.UserDTO;
import com.chatbot.commonlibrary.enums.ConnectionStatus;
import com.chatbot.commonlibrary.enums.NotificationType;
import com.chatbot.commonlibrary.exception.NotFoundException;
import com.chatbot.lawyerconnectorservice.config.LawyerFeignClient;
import com.chatbot.lawyerconnectorservice.config.NotificationFeignClient;
import com.chatbot.lawyerconnectorservice.config.UserFeignClient;
import com.chatbot.lawyerconnectorservice.mapper.LawyerConnectionMapper;
import com.chatbot.lawyerconnectorservice.model.Connection;
import com.chatbot.lawyerconnectorservice.repository.ConnectionRepository;
import com.chatbot.lawyerconnectorservice.service.ConnectionService;
import com.chatbot.lawyerservice.model.Lawyer;
import com.chatbot.lawyerservice.repository.LawyerRepository;
import com.chatbot.userservice.model.User;
import com.chatbot.userservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository repository;
    private final LawyerConnectionMapper mapper;
    private final LawyerFeignClient lawyerClient;
    private final UserFeignClient userClient;

    @Override
    public LawyerConnectionDTO createConnection(LawyerConnectionDTO dto) {
        // Nouvelle connexion sans ID (évite tout update)
        Connection connection = new Connection();
        connection.setUserId(dto.getUserId());
        connection.setLawyerId(dto.getLawyerId());
        connection.setObjet(dto.getObjet());
        connection.setCaseDescription(dto.getCaseDescription());
        connection.setStatus(dto.getStatus() != null ? dto.getStatus() : ConnectionStatus.PENDING);
        connection.setRequestDate(dto.getRequestDate() != null ? dto.getRequestDate() : Instant.now());

        // Enregistrement
        Connection saved = repository.save(connection);
        return enrich(mapper.toDto(saved));
    }

    @Override
    public LawyerConnectionDTO getConnectionById(Long id) {
        Connection connection = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Connection not found"));

        return enrich(mapper.toDto(connection));
    }

    @Override
    public List<LawyerConnectionDTO> getConnectionsByUserId(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(connection -> enrich(mapper.toDto(connection)))
                .toList();
    }

    @Override
    public List<LawyerConnectionDTO> getConnectionsByLawyerId(Long lawyerId) {
        return repository.findByLawyerId(lawyerId).stream()
                .map(connection -> enrich(mapper.toDto(connection)))
                .toList();
    }

    @Override
    public List<LawyerConnectionDTO> getConnectionsByStatus(ConnectionStatus status) {
        return repository.findByStatus(status).stream()
                .map(connection -> enrich(mapper.toDto(connection)))
                .toList();
    }

    @Override
    public LawyerConnectionDTO updateConnection(Long id, LawyerConnectionDTO updatedDto) {
        Connection existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Connexion non trouvée"));

        existing.setStatus(updatedDto.getStatus());

        if (updatedDto.getCaseDescription() != null) {
            existing.setCaseDescription(updatedDto.getCaseDescription());
        }

        return enrich(mapper.toDto(repository.save(existing)));
    }

    @Override
    public void deleteConnection(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void cancel(Long id) {
        Connection connection = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Connexion non trouvée"));
        connection.setStatus(ConnectionStatus.REJECTED);
        repository.save(connection);
    }

    private LawyerConnectionDTO enrich(LawyerConnectionDTO dto) {
        try {
            dto.setUser(userClient.getUserById(dto.getUserId()));
        } catch (Exception e) {
            log.warn("❗ Impossible d'enrichir l'utilisateur ID {}: {}", dto.getUserId(), e.getMessage());
        }

        try {
            dto.setLawyer(lawyerClient.getLawyerById(dto.getLawyerId()));
        } catch (Exception e) {
            log.warn("❗ Impossible d'enrichir l'avocat ID {}: {}", dto.getLawyerId(), e.getMessage());
        }

        return dto;
    }


}
