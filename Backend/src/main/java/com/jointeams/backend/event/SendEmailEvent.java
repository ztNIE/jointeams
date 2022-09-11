package com.jointeams.backend.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public abstract class SendEmailEvent extends ApplicationEvent {
    private String email;
    private String url;

    public SendEmailEvent(String email, String url) {
        super(email);
        this.email = email;
        this.url = url;
    }
}
