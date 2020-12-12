package com.example.dory;

public class UserModel {
    private String firstName;
    private String lastName;
    private String avatar;
    private String score;


    public String getDisplayName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String newFirstName) {
        firstName = newFirstName;
    }

    public void setLastName(String newLastName) {
        lastName = newLastName;
    }

    public void setAvatar(String newLastName) {
        avatar = newLastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setScore(String newScore) {
        score = newScore;
    }

    public String getScore() {
        return score;
    }
}
