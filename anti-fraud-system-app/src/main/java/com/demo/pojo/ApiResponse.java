package com.demo.pojo;

public record ApiResponse<T> (
        String status,
        String code,
        String message,
        T data,
        long timestamp
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("success", "OK", null, data, System.currentTimeMillis());
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>("error", null, message, null, System.currentTimeMillis());
    }
}
