package com.example.fleettrack.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RedisNotificationSubscriber {

    private final List<String> notifications = new ArrayList<>();

    public void onMessage(String message) {
        log.info("🔔 [REDIS BİLDİRİŞİ]: {}", message);
        notifications.add(message);
    }

    public List<String> getNotifications() {
        return notifications;
    }
}