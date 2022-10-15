package com.jointeams.backend.model.request;

import lombok.Data;

@Data
public class ReCaptchaRequest {
    private String token;
    private String action;
}
