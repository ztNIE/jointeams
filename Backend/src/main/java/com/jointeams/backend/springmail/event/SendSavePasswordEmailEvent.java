package com.jointeams.backend.springmail.event;

import com.jointeams.backend.springmail.SendEmailEvent;

public class SendSavePasswordEmailEvent extends SendEmailEvent {

    private final String path = "/register/savePassword";

    public SendSavePasswordEmailEvent(String email, String url) {
        super(email, url);
        setApplicationUrl(url + path);
        setBody("Click the link to reset your password: " + url);
        setSubject("Jointeams | Reset your password");
    }
}
