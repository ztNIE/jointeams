package com.jointeams.backend.springmail.event;

import com.jointeams.backend.springmail.SendEmailEvent;

public class SendVerifyEmailEvent extends SendEmailEvent {

    private final String path = "/register/verify";
    public SendVerifyEmailEvent(String email, String url) {
        super(email, url);
        setApplicationUrl(url + path);
        setBody("Click the link to verify your account: " + url);
        setSubject("Jointeams | Verify your account");
    }
}
