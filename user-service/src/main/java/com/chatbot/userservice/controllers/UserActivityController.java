package com.chatbot.userservice.controllers;

import com.chatbot.userservice.entities.UserActivity;
import com.chatbot.userservice.dtos.request.LogActivityRequest;
import com.chatbot.userservice.entities.enums.ActivityType;
import com.chatbot.userservice.services.UserActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/userActivity")
@RequiredArgsConstructor
public class UserActivityController {
    private final UserActivityService userActivityService;

    @PostMapping
    public ResponseEntity<UserActivity> createActivity(@RequestBody UserActivity activity) {
        activity.setTimestamp(LocalDateTime.now());
        UserActivity created = userActivityService.createActivity(activity);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserActivity> getActivityById(@PathVariable Long id) {
        return userActivityService.getActivityById(id)
                .map(activity -> new ResponseEntity<>(activity, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserActivity>> getActivitiesByUserId(@PathVariable Long userId) {
        List<UserActivity> activities = userActivityService.getActivitiesByUserId(userId);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<UserActivity>> getActivitiesByType(@PathVariable ActivityType type) {
        List<UserActivity> activities = userActivityService.getActivitiesByType(type);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<UserActivity>> getActivitiesByDateRange(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        List<UserActivity> activities = userActivityService.getActivitiesByDateRange(start, end);
        return ResponseEntity.ok(activities);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserActivity> updateActivity(@PathVariable Long id, @RequestBody UserActivity activity) {
        activity.setActivityId(id); // Assure-toi que l'ID est mis à jour correctement
        UserActivity updated = userActivityService.updateActivity(activity);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        userActivityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/log/{userId}")
    public ResponseEntity<UserActivity> logActivity(@RequestBody LogActivityRequest request, @PathVariable Long userId) {
        UserActivity userActivity = userActivityService.logActivity(request, userId);
        return ResponseEntity.ok(userActivity);
    }
}
