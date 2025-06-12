package com.chatbot.userservice.services.servicesImpl;

import com.chatbot.userservice.entities.User;
import com.chatbot.userservice.entities.UserActivity;
import com.chatbot.userservice.entities.enums.ActivityType;
import com.chatbot.userservice.repository.UserActivityRepository;
import com.chatbot.userservice.repository.UserRepository;
import com.chatbot.userservice.dtos.request.LogActivityRequest;
import com.chatbot.userservice.services.UserActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserActivityServiceImpl implements UserActivityService {
    private final UserActivityRepository userActivityRepository;
    private final UserRepository userRepository;

    @Override
    public UserActivity createActivity(UserActivity activity) {
        // 1. Récupérer le userId
        Long userId = activity.getUser().getUserId();

        // 2. Charger le User existant depuis la base
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id: " + userId));

        // 3. Rattacher le user existant
        activity.setUser(user);

        // 4. Sauvegarder l'activité
        return userActivityRepository.save(activity);
    }

    @Override
    public Optional<UserActivity> getActivityById(Long id) {
        return userActivityRepository.findById(id);
    }

    @Override
    public List<UserActivity> getActivitiesByUserId(Long userId) {
        return userActivityRepository.findByUser_userId(userId);
    }

    @Override
    public List<UserActivity> getActivitiesByType(ActivityType type) {
        return userActivityRepository.findByActivityType(type);
    }

    @Override
    public List<UserActivity> getActivitiesByDateRange(LocalDateTime start, LocalDateTime end) {
        return userActivityRepository.findByTimestampBetween(start, end);
    }

    @Override
    public UserActivity updateActivity(UserActivity activity) {
        return userActivityRepository.save(activity);
    }

    @Override
    public void deleteActivity(Long id) {
        userActivityRepository.deleteById(id);
    }


}
