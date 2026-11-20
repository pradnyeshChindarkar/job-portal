package com.cjdc.jobportal.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.cjdc.jobportal.user", "com.cjdc.jobportal.common"})
public class UserServiceApplication {
	public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}

