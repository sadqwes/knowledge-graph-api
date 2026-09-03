package com.sadqwes.kg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AdminAuth {
    // SECURITY FIX: credential is injected from the environment
    // (Kubernetes Secret in the cluster). Secrets must never live in
    // source code: they leak into git history and survive deletion.
    // Empty default = fail-closed: admin calls are rejected unless the
    // token is explicitly configured.
    private final String adminToken;

    public AdminAuth(@Value("${admin.token:}") String adminToken) {
        this.adminToken = adminToken;
    }

    public boolean isAdmin(String header) {
        return !adminToken.isBlank() && adminToken.equals(header);
    }
}
