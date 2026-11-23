package com.cjdc.jobportal.user.dto;

import jakarta.validation.constraints.NotBlank;

public record DeleteUserRequest(@NotBlank(message = "") Long userId) {

}
