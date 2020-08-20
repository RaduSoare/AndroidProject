package com.example.try1;
import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private String fullName;
    private String phoneNumber;
    private String email;
    private HashMap<String, Boolean> visited;

    public User(String fullName, String phoneNumber, String email, HashMap<String, Boolean> visited) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.visited = visited;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashMap<String, Boolean> getVisited() {
        return visited;
    }

    public void setVisited(HashMap<String, Boolean> visited) {
        this.visited = visited;
    }
}
