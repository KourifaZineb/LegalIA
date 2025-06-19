package com.chatbot.notificationservice.services.serviceImpl;

import com.chatbot.commonlibrary.dtos.LawyerConnectionDTO;
import com.chatbot.commonlibrary.dtos.NotificationDTO;
import com.chatbot.notificationservice.config.UserFeignClient;
import com.chatbot.notificationservice.mapper.NotificationMapper;
import com.chatbot.notificationservice.model.Notification;
import com.chatbot.notificationservice.repository.NotificationRepository;
import com.chatbot.notificationservice.services.NotificationService;
import com.chatbot.userservice.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final NotificationMapper mapper;

    private final UserFeignClient userClient;

    public NotificationServiceImpl(NotificationRepository repository, NotificationMapper mapper, UserFeignClient userClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.userClient = userClient;
    }

    @Override
    public NotificationDTO send(NotificationDTO dto) {
        Notification entity = mapper.toEntity(dto);
        entity.setUserId(dto.getUserId());
        entity.setLawyerId(dto.getLawyerId());
        entity.setRead(false);

        // ⏎ Sauvegarde puis enrichissement DTO
        NotificationDTO savedDto = mapper.toDto(repository.save(entity));
        return enrich(savedDto);
    }

    @Override
    public List<NotificationDTO> getUserNotifications(Long userId) {
        return repository.findByUserId(userId).stream().map(notification -> enrich(mapper.toDto(notification))).toList();
    }

    @Override
    public void markAsRead(Long id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        repository.save(notification);
    }

    private NotificationDTO enrich(NotificationDTO dto) {
        try {
            dto.setUserDTO(userClient.getUserById(dto.getUserId()));
        } catch (Exception e) {
            System.out.println("❗ Impossible d'enrichir l'utilisateur ID {}: {}"+ dto.getUserId());
        }
        return dto;
    }

}