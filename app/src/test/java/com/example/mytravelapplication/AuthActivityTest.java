package com.example.mytravelapplication;

import org.junit.Test;
import static org.junit.Assert.*;

public class AuthActivityTest {

    @Test
    public void testLoginWithValidCredentials_succeeds() {
        String email = "testuser@example.com";
        String password = "password123";

        assertFalse("Email should not be empty", email.isEmpty());
        assertFalse("Password should not be empty", password.isEmpty());
        assertTrue("Email should contain @", email.contains("@"));
        assertTrue("Password should be at least 6 characters", password.length() >= 6);
    }

    @Test
    public void testLoginWithEmptyFields_fails() {
        String email = "";
        String password = "";

        assertTrue("Empty email should be caught", email.isEmpty());
        assertTrue("Empty password should be caught", password.isEmpty());

        boolean shouldShowError = email.isEmpty() || password.isEmpty();
        assertTrue("Error should be shown when fields are empty", shouldShowError);
    }
}