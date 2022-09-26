package com.jointeams.backend.springmail;

import com.jointeams.backend.pojo.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class SendAllEmailEvent extends ApplicationEvent {

    private final List<User> users;
    private final String subject = "Jointeams | Teammates comments available now!";
    private final String body = "As the semester coming to an end, it's time to leave your teammates a comment! " +
            "Your comment will help your teammates to find other collaborators next semester and " +
            "you can choose various team tags for your friend!";

    public SendAllEmailEvent(List<User> users) {
        super(users);
        this.users = users;
    }

}
