package com.jointeams.backend.event;

import com.jointeams.backend.pojo.User;

public class SendVerifyEmailEvent extends SendEmailEvent {

    private final String path = "/register/verify";
    public SendVerifyEmailEvent(User user, String url) {
        super(user, url);
    }

    public String getFullUrl() {
        return getUrl()+path;
    }
}
