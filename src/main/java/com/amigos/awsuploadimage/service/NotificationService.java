package com.amigos.awsuploadimage.service;

public interface NotificationService {
    void sendGlobalNotification(String message);
    void sendPrivateNotification(final Long userId);
}
