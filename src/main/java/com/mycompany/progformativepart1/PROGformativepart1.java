
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * to change this license
 */

package com.mycompany.progformativepart1;

import java.util.Scanner;

public class PROGformativepart1 {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // ================= REGISTRATION =================
        System.out.println("=== REGISTRATION ===");

        Login user;
        boolean registered;

        do {
            System.out.print("Enter your first name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter your last name: ");
            String lastName = scanner.nextLine();

            System.out.print("Enter a username: ");
            String username = scanner.nextLine();

            System.out.print("Enter a password: ");
            String password = scanner.nextLine();

            System.out.print("Enter your cell phone number: ");
            String cellphoneNumber = scanner.nextLine();

            // Create the Login object
            user = new Login(
                firstName,
                lastName,
                username,
                password,
                cellphoneNumber
            );

            System.out.println();
            System.out.println(user.registerUser());

            // Check cellphone number
            if (user.checkcellphoneNumber()) {
                System.out.println(
                    "Cell phone number successfully added."
                );
            } else {
                System.out.println(
                    "Cell phone number incorrectly formatted "
                    + "or does not contain international code."
                );
            }

            // Check all registration requirements
            registered = user.checkUserName()
                    && user.checkPasswordComplexity()
                    && user.checkcellphoneNumber();

            if (!registered) {
                System.out.println(
                    "Please try registering again."
                );
                System.out.println();
            }

        } while (!registered);

        // ================= LOGIN =================
        System.out.println();
        System.out.println("======= LOGIN ======");

        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.print("Enter your username: ");
            String loginUsername = scanner.nextLine();

            System.out.print("Enter your password: ");
            String loginPassword = scanner.nextLine();

            System.out.println();

            System.out.println(
                user.returnLoginStatus(
                    loginUsername,
                    loginPassword
                )
            );

            loggedIn = user.loginUser(
                loginUsername,
                loginPassword
            );

            if (!loggedIn) {
                System.out.println(
                    "Please try logging in again."
                );
                System.out.println();
            }
        }

        // ================= QUICKCHAT =================
        System.out.println();
        System.out.println("======= QUICKCHAT ======");
        System.out.println("Welcome to QuickChat.");

        // Ask how many messages the user wants to enter
        int limit = readInt(
            "How many messages do you want to enter? "
        );

        while (limit < 1) {
            limit = readInt(
                "Please enter a number of 1 or more: "
            );
        }

        int entered = 0;
        int choice = 0;

        // Main QuickChat menu
        while (choice != 3) {

            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");

            choice = readInt("Choose an option: ");

            if (choice == 1) {

                if (entered >= limit) {
                    System.out.println(
                        "You have already entered all "
                        + limit + " messages."
                    );
                } else {

                    while (entered < limit) {
                        enterOneMessage();
                        entered++;
                    }

                    System.out.println(
                        "\nTotal messages sent: "
                        + Messages.returnTotalMessages()
                    );
                }

            } else if (choice == 2) {

                // Display previously sent messages
                System.out.println(Messages.printMessages());

            } else if (choice == 3) {

                System.out.println(
                    "Goodbye! Thank you for using QuickChat."
                );

                System.out.println(
                    "Total messages sent: "
                    + Messages.returnTotalMessages()
                );

            } else {

                System.out.println(
                    "Please choose 1, 2 or 3."
                );
            }
        }

        scanner.close();
    }

    // ================= ENTER ONE MESSAGE =================
    static void enterOneMessage() {

        // Validate recipient cellphone number
        String recipient;
        Messages check;

        do {
            System.out.print(
                "Enter recipient cell number "
                + "(e.g. +27718693002): "
            );

            recipient = scanner.nextLine();

            check = new Messages(
                "0000000000",
                0,
                recipient,
                "check"
            );

            String result = check.checkRecipientCell();

            System.out.println(result);

            if (!result.equals(
                    "Cell phone number successfully captured.")) {
                check = null;
            }

        } while (check == null);

        // Validate message text
        String text;

        do {
            System.out.print(
                "Enter your message (max 250 characters): "
            );

            text = scanner.nextLine();

            if (text.length() > 250) {
                System.out.println(
                    "Please enter a message of 250 characters or less."
                );
            } else if (text.trim().isEmpty()) {
                System.out.println(
                    "The message cannot be empty."
                );
            }

        } while (
            text.length() > 250 || text.trim().isEmpty()
        );

        // Create the message
        Messages message = new Messages(recipient, text);

        System.out.println(message.messageIDGenerated());
        System.out.println(message.checkMessageLength());

        // Choose what to do with the message
        System.out.println("\n1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");

        int option = readInt("Choose an option: ");

        while (option < 1 || option > 3) {
            option = readInt("Please choose 1, 2 or 3: ");
        }

        String result = message.sentMessage(option);
        System.out.println(result);

        if (option == 1) {

            // Display full details, including the hash
            System.out.println("\n" + message.getDetails());

        } else if (option == 2) {

            // Confirm that the message was disregarded
            System.out.println("Message disregarded.");

        } else if (option == 3) {

            // Storage confirmation is returned by sentMessage()
            // No extra print statement is needed here.
        }
    }

    // ================= READ AN INTEGER =================
    static int readInt(String prompt) {

        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();

            try {
                return Integer.parseInt(line.trim());

            } catch (NumberFormatException e) {
                System.out.println(
                    "That is not a number. Please try again."
                );
            }
        }
    }
}