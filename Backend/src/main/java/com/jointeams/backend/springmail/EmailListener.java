package com.jointeams.backend.springmail;

import com.jointeams.backend.pojo.User;
import com.jointeams.backend.pojo.token.PasswordToken;
import com.jointeams.backend.repositery.PasswordTokenRepository;
import com.jointeams.backend.repositery.UserRepository;
import com.jointeams.backend.service.RegisterService;
import com.jointeams.backend.service.serviceImpl.EmailSenderService;
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

    @Autowired
    PasswordTokenRepository passwordTokenRepository;

    @Async
    @EventListener
    public void handleEmailEvent(SendEmailEvent event) throws ClassNotFoundException {
        User user = (User) userRepository.findByEmail(event.getEmail()).orElse(null);
        String token = UUID.randomUUID().toString();
        if (event.getEmailType() == EmailType.VERIFY) {
            registerService.saveVerificationTokenForUser(token, user);
        } else {
            PasswordToken passwordToken = new PasswordToken(token);
            passwordToken.setUser(user);
            passwordTokenRepository.save(passwordToken);
        }
        log.debug("listener get event");
        emailSenderService.sendSimpleNoReplyEmail(
                event.getEmail(),
                event.getBody() + token,
                event.getSubject());
    }

    @Async
    @EventListener
    public void handleSendAllEmailEvent(SendAllEmailEvent event) {
        for (User user: event.getUsers()) {
            emailSenderService.sendSimpleNoReplyEmail(
                    user.getEmail(),
                    event.getBody(),
                    event.getSubject()
            );
        }
    }
}
