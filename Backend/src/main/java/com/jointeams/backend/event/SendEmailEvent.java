package com.jointeams.backend.event;

import com.jointeams.backend.pojo.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public abstract class SendEmailEvent extends ApplicationEvent {
    private User user;
    private String url;

    public SendEmailEvent(User user, String url) {
        super(user);
        this.user = user;
        this.url = url;
    }
}
