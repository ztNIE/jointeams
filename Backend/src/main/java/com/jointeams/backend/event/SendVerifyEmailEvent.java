package com.jointeams.backend.event;

public class SendVerifyEmailEvent extends SendEmailEvent {

    private final String path = "/register/verify";
    public SendVerifyEmailEvent(String email, String url) {
        super(email, url);
    }

    public String getFullUrl() {
        return getUrl()+path;
    }
}
