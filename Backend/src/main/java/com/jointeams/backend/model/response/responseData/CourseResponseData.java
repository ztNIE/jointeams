package com.jointeams.backend.model.response.responseData;

import com.jointeams.backend.pojo.Group;
import com.jointeams.backend.pojo.University;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseData {
    private Long id;
    private String code;
    private String name;
    private Long universityId;
    private String universityName;
    private Boolean isLocked;
}
