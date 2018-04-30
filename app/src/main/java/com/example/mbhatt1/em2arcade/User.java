package com.example.mbhatt1.em2arcade;

public class User {
    public String ID;
    public String username;
    public String email;
    public String password;
    public String connectHS;
    public String memoryHS;
    public String blackjackHS;


    public User(String username, String email, String password, String connectHS, String memoryHS, String blackjackHS) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.connectHS = connectHS;
        this.memoryHS = memoryHS;
        this.blackjackHS = blackjackHS;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUserame(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConnectHS() {
        return connectHS;
    }

    public void setConnectHS(String connectHS) {
        this.connectHS = connectHS;
    }

    public String getMemoryHS() {
        return memoryHS;
    }

    public void setMemoryHS(String memoryHS) {
        this.memoryHS = memoryHS;
    }

    public String getBlackjackHS() {
        return blackjackHS;
    }

    public void setBlackjackHS(String blackjackHS) {
        this.blackjackHS = blackjackHS;
    }


    @Override
    public String toString() {
        return "User{" +
                "ID='" + ID + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", connectHS='" + connectHS + '\'' +
                ", memoryHS='" + memoryHS + '\'' +
                ", blackjackHS='" + blackjackHS + '\'' +
                '}';
    }
}
