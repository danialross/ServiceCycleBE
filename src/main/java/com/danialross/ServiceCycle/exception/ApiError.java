package com.danialross.ServiceCycle.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ApiError {
    private int statusCode;
    private Object messages;
    private LocalDateTime timeStamp;
}
