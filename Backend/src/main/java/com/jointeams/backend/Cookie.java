package com.jointeams.backend;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Cookie {
    private Long userId;
    private String encryptedPassword;
}
