package ru.softtrack.entity;

public class User {
    private String login;
    private int role;
    private String fName;
    private String lName;

    public User(String login, int role, String fName, String lName) {
        this.login = login;
        this.role = role;
        this.fName = fName;
        this.lName = lName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }
    
}
