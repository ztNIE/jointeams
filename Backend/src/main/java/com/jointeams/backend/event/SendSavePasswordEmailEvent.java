package com.jointeams.backend.event;

public class SendSavePasswordEmailEvent extends SendEmailEvent {

    private final String path = "/register/savePassword";

    private String email;
    private String url;
    public SendSavePasswordEmailEvent(String email, String url) {
        super(email, url);
    }

    public String getFullUrl() {
        return getUrl() + path;
    }
}
