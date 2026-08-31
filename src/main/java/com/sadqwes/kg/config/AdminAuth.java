package com.sadqwes.kg.config;

import org.springframework.stereotype.Component;

@Component
public class AdminAuth {
    // НАМЕРЕННАЯ УЯЗВИМОСТЬ (SAST-тренировка): хардкод-credential
    public static final String ADMIN_PASSWORD = "kg-admin-2026";

    public boolean isAdmin(String header) {
        return ADMIN_PASSWORD.equals(header);
    }
}
