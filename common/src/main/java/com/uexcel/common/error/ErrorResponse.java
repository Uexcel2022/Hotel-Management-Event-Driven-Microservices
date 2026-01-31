package com.uexcel.common.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String timestamp;
    private int status;
    private String errorMessage;
    private String error;
    private String path;
}
