package com.amigos.awsuploadimage.controller;

import com.amigos.awsuploadimage.request.Message;
import com.amigos.awsuploadimage.response.ResponseMessage;
import com.amigos.awsuploadimage.service.impl.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("")
    public String getAdminPage() {
        return "pages/admin/index";
    }

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public ResponseMessage getMessage(final Message message){
        notificationService.sendGlobalNotification(message.getMessageContent());
        return new ResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()));
    }
}
