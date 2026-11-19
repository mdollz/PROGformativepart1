package com.mycompany.part2prog;

import java.util.Scanner;

// Main class for QuickChat
public class QuickChat {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // REGISTRATION
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

            user = new Login(firstName, lastName, username,
                    password, cellphoneNumber);

            System.out.println();
            System.out.println(user.registerUser());

            if (user.checkcellphoneNumber()) {
                System.out.println("Cell phone number successfully added.");
            } else {
                System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            }

            registered = user.checkUserName()
                    && user.checkPasswordComplexity()
                    && user.checkcellphoneNumber();

            if (!registered) {
                System.out.println("Please try registering again.");
                System.out.println();
            }

        } while (!registered);

        // LOGIN
        System.out.println();
        System.out.println("=== LOGIN ===");

        boolean loggedIn = false;

        while (!loggedIn) {

            System.out.print("Enter your username: ");
            String loginUsername = scanner.nextLine();

            System.out.print("Enter your password: ");
            String loginPassword = scanner.nextLine();

            System.out.println();

            System.out.println(user.returnLoginStatus(
                    loginUsername, loginPassword));

            loggedIn = user.loginUser(loginUsername, loginPassword);
        }

        System.out.println();
        System.out.println("Welcome to QuickChat.");

        // Registered cellphone number becomes the sender
        String sender = user.getCellphoneNumber();

        // Number of messages the user wants to enter
        int limit = readInt("How many messages do you want to enter? ");

        while (limit < 1) {
            limit = readInt("Please enter a number of 1 or more: ");
        }

        int entered = 0;
        int choice = 0;

        // MAIN MENU
        while (choice != 3) {

            System.out.println("\n===== QUICKCHAT MENU =====");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");

            choice = readInt("Choose an option: ");

            if (choice == 1) {

                if (entered >= limit) {
                    System.out.println("You have already entered all "
                            + limit + " messages.");
                } else {

                    while (entered < limit) {
                        enterOneMessage(sender);
                        entered++;
                    }

                    System.out.println("\nTotal messages sent: "
                            + Message.returnTotalMessages());
                }

            } else if (choice == 2) {

                System.out.println("\n=== RECENTLY SENT MESSAGES ===");

                String messages = Message.printMessages();

                if (messages.isEmpty()) {
                    System.out.println("No sent messages available.");
                } else {
                    System.out.println(messages);
                }

            } else if (choice == 3) {

                System.out.println("Goodbye! Total messages sent: "
                        + Message.returnTotalMessages());

            } else if (choice == 4) {

                storedMessagesMenu();

            } else {

                System.out.println("Please choose 1, 2, 3 or 4.");
            }
        }
    }

    // Enter one message
    static void enterOneMessage(String sender) {

        // Validate recipient
        String recipient;
        Message check;

        do {
            System.out.print("Enter recipient cell number (e.g. +27718693002): ");
            recipient = scanner.nextLine();

            check = new Message("0000000000", 0,
                    recipient, "check");

            String result = check.checkRecipientCell();

            System.out.println(result);

            if (!result.equals("Cell phone number successfully captured.")) {
                check = null;
            }

        } while (check == null);

        // Validate message text
        String text;

        do {
            System.out.print("Enter your message (max 250 characters): ");
            text = scanner.nextLine();

            if (text.length() > 250) {
                System.out.println("Please enter a message of less than 250 characters.");
            } else if (text.trim().isEmpty()) {
                System.out.println("The message cannot be empty.");
            }

        } while (text.length() > 250 || text.trim().isEmpty());

        // Create message with sender and recipient
        Message message = new Message(sender, recipient, text);

        System.out.println(message.messageIDGenerated());
        System.out.println(message.checkMessageLength());

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

            System.out.println("\n" + message.getDetails());

        } else if (option == 2) {

            System.out.print("Enter 0 to confirm disregard: ");
            String answer = scanner.nextLine();

            if (answer.trim().equals("0")) {

                message.disregardMessage();

                System.out.println("Message disregarded.");

            } else {

                System.out.println("Message was not sent.");
            }
        }
    }

    // STORED MESSAGES MENU
    static void storedMessagesMenu() {

        int option = 0;

        while (option != 7) {

            System.out.println("\n===== STORED MESSAGES =====");
            System.out.println("1) Display stored message senders and recipients");
            System.out.println("2) Display the longest stored message");
            System.out.println("3) Search for a message by ID");
            System.out.println("4) Search messages by recipient");
            System.out.println("5) Delete a message using its hash");
            System.out.println("6) Display stored messages report");
            System.out.println("7) Return to main menu");

            option = readInt("Choose an option: ");

            switch (option) {

                case 1:
                    System.out.println("\n=== SENDERS AND RECIPIENTS ===");
                    System.out.println(Message.displayStoredMessages());
                    break;

                case 2:
                    System.out.println("\n=== LONGEST STORED MESSAGE ===");
                    System.out.println(Message.displayLongestMessage());
                    break;

                case 3:
                    System.out.print("Enter message ID: ");
                    String id = scanner.nextLine();

                    System.out.println(Message.searchMessageByID(id));
                    break;

                case 4:
                    System.out.print("Enter recipient number: ");
                    String recipient = scanner.nextLine();

                    System.out.println(
                            Message.searchMessagesByRecipient(recipient));
                    break;

                case 5:
                    System.out.print("Enter message hash: ");
                    String hash = scanner.nextLine();

                    System.out.println(Message.deleteMessageByHash(hash));
                    break;

                case 6:
                    System.out.println("\n=== STORED MESSAGE REPORT ===");
                    System.out.println(Message.displayReport());
                    break;

                case 7:
                    System.out.println("Returning to main menu.");
                    break;

                default:
                    System.out.println("Please choose a number between 1 and 7.");
            }
        }
    }

    // Reads a whole number without crashing if letters are entered
    static int readInt(String prompt) {

        while (true) {

            System.out.print(prompt);
            String line = scanner.nextLine();

            try {
                return Integer.parseInt(line.trim());

            } catch (NumberFormatException e) {

                System.out.println("That is not a number. Try again.");
            }
        }
    }
}
