package com.amigos.awsuploadimage.api;

import com.amigos.awsuploadimage.request.Message;
import com.amigos.awsuploadimage.service.WSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuyVoucherApi {

    @Autowired
    private WSService wsService;

    @PostMapping("/send-messgae")
    public void sendMessage(@RequestBody Message message){
        wsService.notifyFrontEnd(message.getMessageContent());
    }
}
