package com.cjdc.jobportal.user.dto;

public record SignInResponse(String token, String tokenType, long expiry) {}
