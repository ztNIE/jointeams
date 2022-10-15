package com.jointeams.backend.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReCaptchaResponse implements Serializable {
    private boolean success;
    private String challenge_ts;
    private String hostname;
    private Float score;
    private String action;

    @JsonProperty("error-codes")
    private String[] errorCodes;
}
