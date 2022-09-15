package com.amigos.awsuploadimage.service.impl;

import com.amigos.awsuploadimage.response.ResponseMessage;
import com.amigos.awsuploadimage.service.NotificationService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void sendGlobalNotification(String message) {
//        ResponseMessage messageContent = new ResponseMessage("Global Notification");
        messagingTemplate.convertAndSend("/topic/global-notifications", message);
    }

    @Override
    public void sendPrivateNotification(Long userId) {
        ResponseMessage message = new ResponseMessage("Private Notification");
        messagingTemplate.convertAndSendToUser(String.valueOf(userId),"/topic/private-notifications", message);
    }
}
