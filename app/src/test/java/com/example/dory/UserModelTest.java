package com.example.dory;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserModelTest {
    @Test
    //Test if the user model updates first name correctly
    public void displayUserNameProperly() {
        UserModel userModel = new UserModel("firstName", "lastName");
        assertEquals(userModel.getDisplayName(), "firstName lastName");
    }

    @Test
    //Test if the user model updates first name and last name correctly
    public void updateUserDisplayName() {
        UserModel userModel = new UserModel("firstName", "lastName");
        userModel.setFirstName("New FirstName");
        userModel.setLastName("New LastName");
        assertEquals(userModel.getDisplayName(), "New FirstName New LastName");
    }

    @Test
    //Test if the user model updates avatar correctly
    public void updateUserAvatar() {
        UserModel userModel = new UserModel("firstName", "lastName");
        userModel.setAvatar("https://avatar.png");
        assertEquals(userModel.getAvatar(), "https://avatar.png");
    }

    @Test
    //Test if the user model updates score correctly
    public void updateUserScore() {
        UserModel userModel = new UserModel("firstName", "lastName");
        userModel.setScore(15);
        assertEquals(userModel.getScore(), 15);
    }
}
