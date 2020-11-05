package com.example.dory;

public class UserModel {
    private String firstName;
    private String lastName;
    private String avatar = "";
    private int score = 0;

    public UserModel(String defaultFirstName, String defaultLastName) {
        firstName = defaultFirstName;
        lastName = defaultLastName;
    }


    public String getDisplayName() {
        return firstName + " " + lastName;
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

    public void setScore(int newScore) {
        score = newScore;
    }

    public int getScore() {
        return score;
    }
}
