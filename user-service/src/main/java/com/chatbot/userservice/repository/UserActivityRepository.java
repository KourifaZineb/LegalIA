package com.chatbot.userservice.repository;

import com.chatbot.userservice.entities.UserActivity;
import com.chatbot.userservice.entities.enums.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {

    List<UserActivity> findByUser_userId(Long userId);
    List<UserActivity> findByActivityType(ActivityType activityType);
    List<UserActivity> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
