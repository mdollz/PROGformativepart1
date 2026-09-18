/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.progformativepart1;

/**
 *
 * @author Student
 */
//login clas consiting of registration and login details of the user
public class Login {
    // creating a variable to store the user's details
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellphoneNumber;
    
    // Constructor  this runs when we creating a new Login object
    public Login(String firstName, String lastName, String username, String password, String cellphoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellphoneNumber = cellphoneNumber;
    }
    //Checks that the username contains an underscore
    //  and is not longer than 5 characters.
    public boolean checkUserName() {
        if (username.contains("_") && username.length() <= 5) {
            return true;
        } else {
            return false;
        }
    }
    //Checks that the password is at least 8 characters long and has
     // a capital letter, a number and a special character.
     public boolean checkPasswordComplexity() {

        // Start by saying we have not found anything yet
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        // Password must have  atleast 8 characters
        if (password.length() < 8) {
            return false;
        }
     // Loop through every character in the password and check what it is
        for (int i = 0; i < password.length(); i++) {
            char letter = password.charAt(i);

            if (Character.isUpperCase(letter)) {
                hasCapital = true;
            } else if (Character.isDigit(letter)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(letter)) {
                hasSpecial = true;
            }
        }
        // Only return true if all three were found
        if (hasCapital && hasNumber && hasSpecial) {
            return true;
        } else {
            return false;
        }
    }
     
     // The number must start with the international code +27 and must not have more than 10 digits after it
     public boolean checkcellphoneNumber() {
        if (cellphoneNumber.matches("^\\+27[0-9]{1,10}$")) {
            return true;
        } else {
            return false;
        }
    }
     //Returns the correct message depending on whether the  username and password were entered correctly
     public String registerUser() {

        if (checkUserName() == false) {
            return "Username is not correctly formatted, Please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (checkPasswordComplexity() == false) {
            return "Password is not correctly formatted ,Please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        
        return "Username successfully captured. Password successfully captured.";
    }
    //Checks if the username and password the user typed in  match the ones they registered with
     public boolean loginUser(String enteredUsername, String enteredPassword) {
        if (username.equals(enteredUsername) && password.equals(enteredPassword)) {
            return true;
        } else {
            return false;
        }
    }
     //Returns the welcome message if the login worked or an error message if it did not
     public String returnLoginStatus(String enteredUsername, String enteredPassword) {
        if (loginUser(enteredUsername, enteredPassword)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
  
}
