package com.socksManagementSystem.dto;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public class ErrorResponse {
    private String error;
    private String message;
    private LocalDateTime timestamp;

}
