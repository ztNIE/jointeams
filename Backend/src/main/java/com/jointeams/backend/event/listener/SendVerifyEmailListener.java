package com.jointeams.backend.event.listener;

import com.jointeams.backend.event.SendVerifyEmailEvent;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class SendVerifyEmailListener implements ApplicationListener<SendVerifyEmailEvent> {

    @Autowired
    private RegisterService registerService;

    @Override
    public void onApplicationEvent(SendVerifyEmailEvent event) {
        // Create the Verification Token for the User with link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        registerService.saveVerificationTokenForUser(token, user);
        String url = event.getApplicationUrl() + "/register/verify?token=" + token;
        // TODO String url = event.getApplicationUrl() + "verify?token=" + token;

        // TODO
        // Send Email to the User using gmail
        log.info("Click the link to verify your account: {}", url);


    }
}
