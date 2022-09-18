package com.jointeams.backend.springmail.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public abstract class SendEmailEvent extends ApplicationEvent {
    private String email;
    private String url;

    private String body;
    private String subject;

    public SendEmailEvent(String email, String url) {
        super(email);
        this.email = email;
        this.url = url;
    }
}
