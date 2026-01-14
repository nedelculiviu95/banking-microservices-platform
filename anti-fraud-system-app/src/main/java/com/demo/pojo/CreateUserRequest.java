package com.demo.pojo;

public record CreateUserRequest(
        String name,
        String username,
        String password,
        String role
) {}
