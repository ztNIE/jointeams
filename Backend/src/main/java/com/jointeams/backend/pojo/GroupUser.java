package com.jointeams.backend.pojo;

import com.jointeams.backend.pojo.id.GroupUserId;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class GroupUser {

    @EmbeddedId
    private GroupUserId groupUserId;

    private boolean isLeader;
}