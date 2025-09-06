package com.viiku.frauddetection.common.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private String error;
    private int status;
    private LocalDateTime timestamp;

    public static <T> CustomResponse<T> success(T data, String message, int status) {
        return CustomResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> CustomResponse<T> error(String error, String message, int status) {
        return CustomResponse.<T>builder()
                .success(false)
                .error(error)
                .message(message)
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> CustomResponse<T> error(String error, String message, T data, int status) {
        return CustomResponse.<T>builder()
                .success(false)
                .error(error)
                .message(message)
                .data(data)
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
