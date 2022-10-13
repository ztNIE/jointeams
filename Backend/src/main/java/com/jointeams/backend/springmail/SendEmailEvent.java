package com.jointeams.backend.springmail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SendEmailEvent extends ApplicationEvent {

    private String email;

    private final String applicationUrl = "http://localhost:8888/";
    private EmailType emailType;
    private String path;

    public SendEmailEvent(String email) {
        super(email);
    }

    public SendEmailEvent(String email, EmailType emailType) {
        super(email);
        this.emailType = emailType;
        this.email = email;
        if (emailType.equals(EmailType.VERIFY)) {
            this.path = "verify/register/";
        } else if (emailType.equals(EmailType.RESET_PASSWORD)) {
            this.path = "reset-password/";
        }
    }

    public String getBody() throws ClassNotFoundException {
        if (emailType == EmailType.VERIFY) {
            return "Click the link to verify your account: "+applicationUrl + path;
        } else if (emailType == EmailType.RESET_PASSWORD) {
            return "Click the link to reset your password: "+applicationUrl + path;
        } else {
            throw new ClassNotFoundException("Unknown email type");
        }
    }
    public String getSubject() throws ClassNotFoundException {
        if (emailType == EmailType.VERIFY) {
            return "Jointeams | Verify your email account";
        } else if (emailType == EmailType.RESET_PASSWORD) {
            return "Jointeams | Reset your password";
        } else {
            throw new ClassNotFoundException("Unknown email type");
        }
    }
}
