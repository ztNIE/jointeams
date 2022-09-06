package com.jointeams.backend.pojo;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Cookie {
    private Long userId;
    private String encryptedPassword;
}
