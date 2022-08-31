package com.jointeams.backend;

import com.jointeams.backend.pojo.User;
import com.jointeams.backend.repositery.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

    private Logger logger = LoggerFactory.getLogger("startLogger");

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return (args) -> {
//
//            UserRepository userRepository = (UserRepository) ctx.getBean("userRepository");
//            User user1 = new User();
//            user1.setLastName("lastName");
//            user1.setFirstName("firstName");
//            user1.setActivate(true);
//            user1.setAdmin(false);
//            userRepository.save(user1);
//            logger.info("Add user1: " + user1);
//        };
//    }
}