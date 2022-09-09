package com.jointeams.backend.event;

import com.jointeams.backend.pojo.User;

public class SendResetPasswordEmail extends SendEmailEvent {

    private final String path = "/register/verifyResetPassword";

    private User user;
    private String url;
    public SendResetPasswordEmail(User user, String url) {
        super(user, url);
    }

    public String getFullUrl() {
        return getUrl() + path;
    }
}
