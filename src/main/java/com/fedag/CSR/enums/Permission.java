package com.fedag.CSR.enums;

public enum Permission {
    USER("user"),
    ADMIN("admin");
    private final String permission;
    Permission(String permission) {
        this.permission = permission;
    }
    public String getPermission() {
        return permission;
    }
}

