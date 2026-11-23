package com.cjdc.jobportal.user.dto;

import java.time.LocalDateTime;

import com.cjdc.jobportal.user.entity.Role;

public record UserResponse(Long id, String name, String email, String phone, Role role, LocalDateTime createdAt) {
}
