package com.secondproject.project.vo;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MapVO {
    private Boolean status;
    private String message;
    private HttpStatus code;
}
