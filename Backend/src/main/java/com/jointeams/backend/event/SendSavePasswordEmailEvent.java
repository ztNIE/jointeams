package com.jointeams.backend.event;

import com.jointeams.backend.pojo.User;

public class SendSavePasswordEmailEvent extends SendEmailEvent {

    private final String path = "/register/savePassword";

    private User user;
    private String url;
    public SendSavePasswordEmailEvent(User user, String url) {
        super(user, url);
    }

    public String getFullUrl() {
        return getUrl() + path;
    }
}
