//package com.jointeams.backend.event.listener;
//
//import com.jointeams.backend.event.SendVerifyEmailEvent;
//import com.jointeams.backend.pojo.User;
//import com.jointeams.backend.repositery.UserRepository;
//import com.jointeams.backend.service.RegisterService;
//import com.jointeams.backend.service.serviceImpl.EmailSenderService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
//@Slf4j
//@Component
//public class SendVerifyEmailListener implements ApplicationListener<SendVerifyEmailEvent> {
//
//    @Autowired
//    private RegisterService registerService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private EmailSenderService emailSenderService;
//
//    @Override
//    @Async
//    public void onApplicationEvent(SendVerifyEmailEvent event) {
//        // Create the Verification Token for the User with link
//        User user = userRepository.findByEmail(event.getEmail());
//        String token = UUID.randomUUID().toString();
//        registerService.saveVerificationTokenForUser(token, user);
//        String url = event.getFullUrl() + "?token=" + token;
//        log.info("listener get event");
//        emailSenderService.sendSimpleNoReplyEmail(
//                event.getEmail(),
//                "Click the link to verify your account: " + url,
//                "Jointeams | Verify your email account");
//    }
//}
