package com.amigos.awsuploadimage.service.impl;

import com.amigos.awsuploadimage.response.ResponseMessage;
import com.amigos.awsuploadimage.service.NotificationService;
import com.amigos.awsuploadimage.service.WSService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WSServiceImpl implements WSService {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;

    public WSServiceImpl(SimpMessagingTemplate messagingTemplate, NotificationService notificationService) {
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
    }

    @Override
    public void notifyFrontEnd(String message) {
        ResponseMessage response = new ResponseMessage(message);
        notificationService.sendGlobalNotification(response.getContent());
    }

    @Override
    public void notifyUser(Long id, String message) {

    }
}
