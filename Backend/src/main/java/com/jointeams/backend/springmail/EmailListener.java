package com.jointeams.backend.springmail;

import com.jointeams.backend.pojo.User;
import com.jointeams.backend.repositery.UserRepository;
import com.jointeams.backend.service.RegisterService;
import com.jointeams.backend.service.serviceImpl.EmailSenderService;
import com.jointeams.backend.springmail.event.SendEmailEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@EnableAsync
public class EmailListener {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private EmailSenderService emailSenderService;

    @Async
    @EventListener
    public void handleEmailEvent(SendEmailEvent event) {
        User user = userRepository.findByEmail(event.getEmail());
        String token = UUID.randomUUID().toString();
        registerService.saveVerificationTokenForUser(token, user);
        log.info("listener get event");
        emailSenderService.sendSimpleNoReplyEmail(
                event.getEmail(),
                event.getBody(),
                event.getSubject());
    }
}
