package com.jointeams.backend.springmail.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@Builder
public abstract class SendEmailEvent extends ApplicationEvent {
    private String email;
    private String url;

    private String body;
    private String subject;
//    private String path = "/register/savePassword";
    private String token;

    public SendEmailEvent(String email, String url) {
        super(email);
        this.email = email;
        this.url = url;
    }
}
