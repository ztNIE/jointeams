package com.jointeams.backend.springmail;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@Builder
public class SendEmailEvent extends ApplicationEvent {
    private String email;
    private String applicationUrl;
    private String path;
    private String token;
    private EmailType emailType;

    public SendEmailEvent(String email, String applicationUrl) {
        super(email);
    }

    public String getBody() throws ClassNotFoundException {
        if (emailType == EmailType.VERIFY) {
            return "Click the link to verify your account: "+applicationUrl + path+"?token="+
        } else if (emailType == EmailType.RESET_PASSWORD) {

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
