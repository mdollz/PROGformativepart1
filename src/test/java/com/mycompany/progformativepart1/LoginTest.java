/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.progformativepart1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class LoginTest {
    
   @Test
    public void testRegisterUserUsernameIncorrect() {
        Login user = new Login("Kyle", "Smith", "kyle!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Username is not correctly formatted, Please ensure that your username contains an underscore and is no more than five characters in length.",
                user.registerUser());
    }

    @Test
    public void testRegisterUserPasswordIncorrect() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "password", "+27838968976");
        assertEquals("Password is not correctly formatted ,Please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.",
                user.registerUser());
    }
     @Test
    public void testLoginSuccessfulMessage() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Welcome Kyle, Smith it is great to see you again.",
                user.returnLoginStatus("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailedMessage() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Username or password incorrect, please try again.",
                user.returnLoginStatus("kyl_1", "wrongpassword"));
    }
    
    @Test
    public void testUsernameCorrectlyFormatted() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(user.checkUserName());
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        Login user = new Login("Kyle", "Smith", "kyle!!!!!!", "Ch&&sec@ke99!",   "+27838968976");
        assertFalse(user.checkUserName());
    }

    @Test
    public void testPasswordMeetsComplexity() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(user.checkPasswordComplexity());
    }
    @Test
    public void testPasswordDoesNotMeetComplexity() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "password", "+27838968976");
        assertFalse(user.checkPasswordComplexity());
    }

    @Test
    public void testCellphoneCorrectlyFormatted() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(user.checkcellphoneNumber());
    }

    @Test
    public void testcellphoneIncorrectlyFormatted() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "08966553");
        assertFalse(user.checkcellphoneNumber());
    }
     @Test
    public void testLoginSuccessful() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(user.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailed() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(user.loginUser("kyl_1", "wrongpassword"));
    }
}