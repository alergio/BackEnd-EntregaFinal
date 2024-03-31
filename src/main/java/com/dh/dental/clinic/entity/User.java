package com.dh.dental.clinic.entity;

public class User {
    
    private Long id;
    private Role role;
    private String userName;
    private String password;

    public User() {
    }

    public User(Role role, String userName, String password) {
        this.role = role;
        this.userName = userName;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
