package com.chatbot.useractivityservice.services.servicesImpl;

import com.chatbot.commonlibrary.dtos.UserActivityDTO;
import com.chatbot.useractivityservice.config.UserFeignClient;
import com.chatbot.useractivityservice.mapper.UserActivityMapper;
import com.chatbot.useractivityservice.model.UserActivity;
import com.chatbot.useractivityservice.repository.UserActivityRepository;
import com.chatbot.useractivityservice.services.UserActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserActivityServiceImpl implements UserActivityService {

    private final UserActivityRepository repository;
    private final UserActivityMapper mapper;
    private final UserFeignClient userClient;

/*
    @Override
    public UserActivityDTO logActivity(UserActivityDTO dto) {
        UserActivity entity = mapper.toEntity(dto);
        entity.setTimestamp(Instant.now());
        UserActivityDTO savedDto = mapper.toDto(repository.save(entity));
        savedDto.setUserDTO(userClient.getUserById(entity.getUserId()));
        return savedDto;
    }
*/
@Override
public UserActivityDTO logActivity(UserActivityDTO dto) {
    // ⚠️ Ne surtout pas garder l'ID si présent dans le DTO
    dto.setId(null); // <-- indispensable pour forcer l'INSERT

    // Mapping manuel (évite que MapStruct copie un ancien ID)
    UserActivity entity = new UserActivity();
    entity.setUserId(dto.getUserId());
    entity.setActivityType(dto.getActivityType());
    entity.setDetails(dto.getDetails());
    entity.setTimestamp(Instant.now());

    // Sauvegarde (insert)
    UserActivity saved = repository.save(entity);

    // Préparation réponse
    UserActivityDTO savedDto = new UserActivityDTO();
    savedDto.setId(saved.getId());
    savedDto.setUserId(saved.getUserId());
    savedDto.setActivityType(saved.getActivityType());
    savedDto.setDetails(saved.getDetails());
    savedDto.setTimestamp(saved.getTimestamp());

    // Enrichir avec UserDTO (via Feign client)
    savedDto.setUserDTO(userClient.getUserById(saved.getUserId()));

    return savedDto;
}


    @Override
    public List<UserActivityDTO> getActivitiesByUserId(Long userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(activity -> {
                    UserActivityDTO dto = mapper.toDto(activity);
                    dto.setUserDTO(userClient.getUserById(userId));
                    return dto;
                })
                .toList();
    }


    @Override
    public List<UserActivityDTO> getAllActivities() {
        return repository.findAll()
                .stream()
                .map(activity -> {
                    UserActivityDTO dto = mapper.toDto(activity);
                    dto.setUserDTO(userClient.getUserById(activity.getUserId()));
                    return dto;
                })
                .toList();
    }

}
