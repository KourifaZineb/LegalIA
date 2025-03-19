/*
package com.chatbot.userservice.repository;

import com.chatbot.userservice.entities.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {

    List<UserActivity> findByUserId(Integer userId);
    List<UserActivity> findByActivityType(String activityType);
    List<UserActivity> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
*/
