package org.EventManagement.models;

public class User {
    private int id;
    private String userName;
    private String password;
    private String role;

    public User(int id, String role, String password, String userName) {
        this.id = id;
        this.role = role;
        this.password = password;
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
