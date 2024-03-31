package com.dh.dental.clinic.entity;

public class Role {
    
    private Long id;
    private String roleType;

    public Role() {
    }

    public Role(String roleType) {
        this.roleType = roleType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}
