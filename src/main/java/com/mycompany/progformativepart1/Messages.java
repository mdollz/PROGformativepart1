
package com.mycompany.progformativepart1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Messages {

    private static int totalSent = 0;
    private static int nextNumber = 0;

    // No arrays or ArrayLists are used.
    // Sent message details are kept in a StringBuilder.
    private static StringBuilder sentHistory = new StringBuilder();

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    // Constructor for a new message
    public Messages(String recipient, String messageText) {
        this(generateMessageID(), nextNumber, recipient, messageText);
        nextNumber++;
    }

    // Constructor with all message details
    public Messages(String messageID, int messageNumber,
                    String recipient, String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    // Generate a random 10-digit message ID
    private static String generateMessageID() {
        Random random = new Random();
        String id = "";

        for (int i = 0; i < 10; i++) {
            id += random.nextInt(10);
        }

        return id;
    }

    // Check message ID length
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // Validate recipient cellphone number
    
public String checkRecipientCell() {
    if (recipient.matches("^\\+27[6-8][0-9]{8}$")) {
        return "Cell phone number successfully captured.";
    } else {
        return "Cell phone number is incorrectly formatted. "
                + "Enter a South African cellphone number "
                + "starting with +27 and followed by 9 digits.";
    }
}

    // Check message length
    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int extra = messageText.length() - 250;
            return "Message exceeds 250 characters by "
                    + extra + ", please reduce the size.";
        }
    }

    // Create the message hash without using arrays
    public String createMessageHash() {
        String trimmedText = messageText.trim();

        String firstWord;
        String lastWord;

        int firstSpace = trimmedText.indexOf(' ');
        int lastSpace = trimmedText.lastIndexOf(' ');

        if (trimmedText.isEmpty()) {
            firstWord = "";
            lastWord = "";
        } else if (firstSpace == -1) {
            firstWord = trimmedText;
            lastWord = trimmedText;
        } else {
            firstWord = trimmedText.substring(0, firstSpace);
            lastWord = trimmedText.substring(lastSpace + 1);
        }

        firstWord = cleanWord(firstWord);
        lastWord = cleanWord(lastWord);

        String firstTwo = messageID.substring(0, 2);

        return (firstTwo + ":" + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();
    }

    // Remove punctuation from words
    private String cleanWord(String word) {
        return word.replaceAll("[^A-Za-z0-9]", "");
    }

    public String messageIDGenerated() {
        return "Message ID generated: " + messageID;
    }

    // 1 = send, 2 = disregard, 3 = store
    public String sentMessage(int choice) {
        if (choice == 1) {
            totalSent++;

            if (sentHistory.length() > 0) {
                sentHistory.append("\n\n");
            }

            sentHistory.append(getDetails());

            return "Message successfully sent.";

        } else if (choice == 2) {
            return "Press 0 to delete the message.";

        } else if (choice == 3) {
            storeMessage();
            return "Message stored to send later.";

        } else {
            return "Invalid option.";
        }
    }

    // Display messages sent during this program run
    public static String printMessages() {
        if (sentHistory.length() == 0) {
            return "No messages have been sent yet.";
        }

        return sentHistory.toString();
    }

    public static int returnTotalMessages() {
        return totalSent;
    }

    // Save a message in a JSON file without using arrays
    public void storeMessage() {
        File file = new File("storedMessages.json");
        StringBuilder existingJson = new StringBuilder();

        // Read existing JSON, if the file already exists
        if (file.exists()) {
            try (BufferedReader reader =
                    new BufferedReader(new FileReader(file))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    existingJson.append(line).append("\n");
                }

            } catch (IOException e) {
                System.out.println(
                        "Could not read stored messages: " + e.getMessage());
                return;
            }
        }

        String existing = existingJson.toString().trim();
        String newObject =
                "  {\n"
                + "    \"messageID\": \"" + escape(messageID) + "\",\n"
                + "    \"messageHash\": \"" + escape(messageHash) + "\",\n"
                + "    \"recipient\": \"" + escape(recipient) + "\",\n"
                + "    \"message\": \"" + escape(messageText) + "\"\n"
                + "  }";

        String updatedJson;

        if (existing.isEmpty() || existing.equals("[]")) {
            updatedJson = "[\n" + newObject + "\n]";
        } else if (existing.endsWith("]")) {
            String previousObjects =
                    existing.substring(0, existing.length() - 1).trim();

            updatedJson = previousObjects + ",\n"
                    + newObject + "\n]";
        } else {
            System.out.println("The JSON file has an invalid format.");
            return;
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(updatedJson);
        } catch (IOException e) {
            System.out.println(
                    "Could not save the message: " + e.getMessage());
        }
    }

    // Escape characters so the JSON remains valid
    private String escape(String text) {
        return text.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }

    // Display full message details
    public String getDetails() {
        return "Message ID: " + messageID + "\n"
                + "Message Hash: " + messageHash + "\n"
                + "Recipient: " + recipient + "\n"
                + "Message: " + messageText;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    // Reset program data for tests
    public static void resetForTests() {
        sentHistory.setLength(0);
        totalSent = 0;
        nextNumber = 0;
    }
}