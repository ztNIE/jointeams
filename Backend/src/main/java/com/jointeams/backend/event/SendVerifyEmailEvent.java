package com.jointeams.backend.event;

import com.jointeams.backend.pojo.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SendVerifyEmailEvent extends ApplicationEvent {

    private User user;
    private String applicationUrl;

    public SendVerifyEmailEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
