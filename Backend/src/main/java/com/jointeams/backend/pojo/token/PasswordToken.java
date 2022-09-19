package com.jointeams.backend.pojo.token;

import com.jointeams.backend.pojo.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class PasswordToken{
    private static final int EXPIRATION_TIME = 10;
    @Id
    private Long id;

    private String token;

    private Date expirationTime;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    private User user;

    public PasswordToken(String token, User user) {
        super();
        this.token = token;
        this.user = user;
        this.expirationTime = calculateExpirationTime(EXPIRATION_TIME);
    }

    private Date calculateExpirationTime(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }
}
