package com.jointeams.backend.event.listener;

import com.jointeams.backend.event.SendResetPasswordEmail;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class SendResetPasswordEmailListener implements ApplicationListener<SendResetPasswordEmail> {

    @Autowired
    private RegisterService registerService;

    @Override
    public void onApplicationEvent(SendResetPasswordEmail event) {
        // Create the Verification Token for the User with link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        registerService.savePasswordToken(user, token);
        String url = event.getFullUrl() + "?token=" + token;

        // TODO
        // Send Email to the User using gmail
        log.info("Click the link to verify your account: {}", url);
    }
}
