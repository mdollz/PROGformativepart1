
package com.mycompany.progformativepart1;

// Login class containing registration and login details
public class Login {

    // Variables to store the user's details
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellphoneNumber;

    // Constructor
    public Login(String firstName, String lastName, String username,
                 String password, String cellphoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellphoneNumber = cellphoneNumber;
    }

    // Username must contain an underscore and be no longer than 5 characters
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    // Password must have at least 8 characters, a capital letter,
    // a number and a special character
    public boolean checkPasswordComplexity() {

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        if (password.length() < 8) {
            return false;
        }

        for (int i = 0; i < password.length(); i++) {
            char letter = password.charAt(i);

            if (Character.isUpperCase(letter)) {
                hasCapital = true;
            }

            if (Character.isDigit(letter)) {
                hasNumber = true;
            }

            if (!Character.isLetterOrDigit(letter)) {
                hasSpecial = true;
            }
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    // South African mobile number: +27 followed by 9 digits
    public boolean checkcellphoneNumber() {
        return cellphoneNumber.matches("^\\+27[6-8][0-9]{8}$");
    }

    // Return the registration status
    public String registerUser() {

        if (!checkUserName()) {
            return "Username is not correctly formatted. Please ensure "
                    + "that your username contains an underscore and "
                    + "is no more than five characters in length.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted. Please ensure "
                    + "that the password contains at least eight "
                    + "characters, a capital letter, a number, "
                    + "and a special character.";
        }

        if (!checkcellphoneNumber()) {
            return "Cell phone number incorrectly formatted or does "
                    + "not contain a valid South African mobile number.";
        }

        return "Username and password successfully captured.";
    }

    // Check whether the entered login details match
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername)
                && password.equals(enteredPassword);
    }

    // Return the login status message
    public String returnLoginStatus(String enteredUsername,
                                    String enteredPassword) {

        if (loginUser(enteredUsername, enteredPassword)) {
            return "Welcome " + firstName + ", " + lastName
                    + " it's great to see you again.";
        }

        return "Username or password incorrect, please try again.";
    }
}