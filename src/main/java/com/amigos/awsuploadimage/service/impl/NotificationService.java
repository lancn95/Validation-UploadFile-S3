package com.amigos.awsuploadimage.service.impl;

public interface NotificationService {
    void sendGlobalNotification(String message);
    void sendPrivateNotification(final Long userId);
}
