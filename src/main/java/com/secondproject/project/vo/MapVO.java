package com.secondproject.project.vo;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MapVO {
    @Schema(description = "상태")
    private Boolean status;

    @Schema(description = "메세지")
    private String message;

    @Schema(description = "HttpStatus")
    private HttpStatus code;
}
