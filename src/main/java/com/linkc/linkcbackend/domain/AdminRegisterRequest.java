package com.linkc.linkcbackend.domain;

public class AdminRegisterRequest extends RegisterRequest {
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    private Role role;
}
