package com.jointeams.backend.event;

public class SendSavePasswordEmailEvent extends SendEmailEvent {

    private final String path = "/register/savePassword";

    public SendSavePasswordEmailEvent(String email, String url) {
        super(email, url);
        setUrl(url + path);
        setBody("Click the link to reset your password: " + url);
        setSubject("Jointeams | Reset your password");
    }
}
