package com.demo.pojo;

public record CreateUserResponse(
        Long id,
        String name,
        String username,
        String role
) {}
