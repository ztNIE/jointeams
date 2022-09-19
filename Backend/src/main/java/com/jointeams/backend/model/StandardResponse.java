package com.jointeams.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StandardResponse<T> {
    private String msg;
    private T data;
}
