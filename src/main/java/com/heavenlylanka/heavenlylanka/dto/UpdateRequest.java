package com.heavenlylanka.heavenlylanka.dto;

public class UpdateRequest {
    private String name;
    private String email;

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

