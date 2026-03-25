package com.example.auc88.model;

public abstract class User {
    protected String username;
    protected String password;
    protected String fullName;
    protected String role; // "BIDDER", "SELLER", "ADMIN"

    public User(String username, String password, String fullName, String role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    public boolean authenticate(String user, String pass) {
        return username.equals(user) && password.equals(pass);
    }

    public String getUsername() { return username; }
    public String getRole() { return role; }
    public String getFullName() { return fullName; }
}