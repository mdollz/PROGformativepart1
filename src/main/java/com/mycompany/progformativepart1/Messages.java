package com.mycompany.part2prog;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// One Message object represents one message.
public class Message {

    // Arrays required for Part 3
    private static ArrayList<Message> sentMessages = new ArrayList<>();
    private static ArrayList<Message> disregardedMessages = new ArrayList<>();
    private static ArrayList<Message> storedMessages = new ArrayList<>();
    private static ArrayList<String> messageHashes = new ArrayList<>();
    private static ArrayList<String> messageIDs = new ArrayList<>();

    private static int totalSent = 0;
    private static int nextNumber = 0;

    private String messageID;
    private int messageNumber;
    private String sender;
    private String recipient;
    private String messageText;
    private String messageHash;

    // Original Part 2 constructor
    public Message(String recipient, String messageText) {
        this("", generateMessageID(), nextNumber, recipient, messageText);
        nextNumber++;
    }

    // Constructor including sender
    public Message(String sender, String recipient, String messageText) {
        this(sender, generateMessageID(), nextNumber, recipient, messageText);
        nextNumber++;
    }

    // Original constructor kept for existing unit tests
    public Message(String messageID, int messageNumber,
                   String recipient, String messageText) {
        this("", messageID, messageNumber, recipient, messageText);
    }

    // Main constructor
    public Message(String sender, String messageID, int messageNumber,
                   String recipient, String messageText) {

        this.sender = sender;
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    // Generates a random 10-digit message ID
    private static String generateMessageID() {
        Random random = new Random();
        String id = "";

        for (int i = 0; i < 10; i++) {
            id = id + random.nextInt(10);
        }

        return id;
    }

    // Checks message ID length
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // Checks recipient cell phone number
    public String checkRecipientCell() {
        if (recipient.matches("^\\+\\d{1,3}\\d{1,10}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Checks message length
    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int extra = messageText.length() - 250;
            return "Message exceeds 250 characters by " + extra
                    + ", please reduce the size.";
        }
    }

    // Creates the message hash
    public String createMessageHash() {

        String firstTwo = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");

        String firstWord = cleanWord(words[0]);
        String lastWord = cleanWord(words[words.length - 1]);

        return (firstTwo + ":" + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();
    }

    // Removes punctuation from words
    private String cleanWord(String word) {
        return word.replaceAll("[^A-Za-z0-9]", "");
    }

    // Displays generated message ID
    public String messageIDGenerated() {
        return "Message ID generated: " + messageID;
    }

    // Adds message ID and hash to their arrays
    private void addMessageReferences() {

        if (!messageIDs.contains(messageID)) {
            messageIDs.add(messageID);
        }

        if (!messageHashes.contains(messageHash)) {
            messageHashes.add(messageHash);
        }
    }

    // Handles the three message options
    public String sentMessage(int choice) {

        if (choice == 1) {
            sentMessages.add(this);
            totalSent++;
            addMessageReferences();

            return "Message successfully sent.";

        } else if (choice == 2) {

            return "Press 0 to delete the message.";

        } else if (choice == 3) {

            if (!storedMessages.contains(this)) {
                storedMessages.add(this);
            }

            addMessageReferences();
            saveStoredMessages();

            return "Message successfully stored.";

        } else {
            return "Invalid option.";
        }
    }

    // Adds a disregarded message
    public void disregardMessage() {
        disregardedMessages.add(this);
        addMessageReferences();
    }

    // Displays all sent messages
    public static String printMessages() {

        String output = "";

        for (Message m : sentMessages) {
            output += m.getDetails() + "\n";
        }

        return output;
    }

    // Returns the total number of sent messages
    public static int returnTotalMessages() {
        return totalSent;
    }

    // Saves stored messages in a JSON file
    public void storeMessage() {

        if (!storedMessages.contains(this)) {
            storedMessages.add(this);
        }

        addMessageReferences();
        saveStoredMessages();
    }

    // Writes all stored messages to JSON
    private static void saveStoredMessages() {

        String json = "[\n";

        for (int i = 0; i < storedMessages.size(); i++) {

            Message m = storedMessages.get(i);

            json += "  {\n"
                    + "    \"sender\": \"" + escape(m.sender) + "\",\n"
                    + "    \"messageID\": \"" + escape(m.messageID) + "\",\n"
                    + "    \"messageHash\": \"" + escape(m.messageHash) + "\",\n"
                    + "    \"recipient\": \"" + escape(m.recipient) + "\",\n"
                    + "    \"message\": \"" + escape(m.messageText) + "\"\n"
                    + "  }";

            if (i < storedMessages.size() - 1) {
                json += ",";
            }

            json += "\n";
        }

        json += "]";

        try (FileWriter writer = new FileWriter("storedMessages.json")) {
            writer.write(json);

        } catch (IOException e) {
            System.out.println("Could not save the file: " + e.getMessage());
        }
    }

    // Escapes special characters when saving JSON
    private static String escape(String text) {
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // Reads stored messages from the JSON file
    public static void loadStoredMessages() {

        storedMessages.clear();

        StringBuilder json = new StringBuilder();

        try (BufferedReader reader =
                new BufferedReader(new FileReader("storedMessages.json"))) {

            String line;

            while ((line = reader.readLine()) != null) {
                json.append(line);
            }

            Pattern objectPattern =
                    Pattern.compile("\\{(.*?)\\}", Pattern.DOTALL);

            Matcher objectMatcher =
                    objectPattern.matcher(json.toString());

            while (objectMatcher.find()) {

                String object = objectMatcher.group(1);

                String sender = getJsonValue(object, "sender");
                String id = getJsonValue(object, "messageID");
                String hash = getJsonValue(object, "messageHash");
                String recipient = getJsonValue(object, "recipient");
                String text = getJsonValue(object, "message");

                if (id != null && recipient != null && text != null) {

                    if (sender == null) {
                        sender = "";
                    }

                    Message m = new Message(
                            sender, id, nextNumber,
                            recipient, text);

                    if (hash != null) {
                        m.messageHash = hash;
                    }

                    storedMessages.add(m);
                    nextNumber++;

                    if (!messageIDs.contains(m.messageID)) {
                        messageIDs.add(m.messageID);
                    }

                    if (!messageHashes.contains(m.messageHash)) {
                        messageHashes.add(m.messageHash);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Could not read stored messages: "
                    + e.getMessage());
        }
    }

    // Gets a value from a JSON object
    private static String getJsonValue(String object, String key) {

        Pattern pattern = Pattern.compile(
                "\"" + key + "\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"");

        Matcher matcher = pattern.matcher(object);

        if (matcher.find()) {
            return unescape(matcher.group(1));
        }

        return null;
    }

    // Converts escaped JSON characters back to normal characters
    private static String unescape(String text) {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {

            char c = text.charAt(i);

            if (c == '\\' && i + 1 < text.length()) {

                char next = text.charAt(++i);

                switch (next) {
                    case 'n':
                        result.append('\n');
                        break;
                    case 'r':
                        result.append('\r');
                        break;
                    case 't':
                        result.append('\t');
                        break;
                    case '"':
                        result.append('"');
                        break;
                    case '\\':
                        result.append('\\');
                        break;
                    default:
                        result.append(next);
                }

            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    // Displays sender and recipient of stored messages
    public static String displayStoredMessages() {

        loadStoredMessages();

        String output = "";

        for (Message m : storedMessages) {

            output += "Sender: " + m.sender + "\n"
                    + "Recipient: " + m.recipient + "\n\n";
        }

        return output.isEmpty()
                ? "No stored messages found."
                : output;
    }

    // Finds the longest stored message
    public static String displayLongestMessage() {

        loadStoredMessages();

        if (storedMessages.isEmpty()) {
            return "No stored messages found.";
        }

        Message longest = storedMessages.get(0);

        for (Message m : storedMessages) {

            if (m.messageText.length() > longest.messageText.length()) {
                longest = m;
            }
        }

        return longest.messageText;
    }

    // Searches for a message using its ID
    public static String searchMessageByID(String id) {

        loadStoredMessages();

        for (Message m : storedMessages) {

            if (m.messageID.equals(id)) {

                return "Recipient: " + m.recipient
                        + "\nMessage: " + m.messageText;
            }
        }

        return "Message ID not found.";
    }

    // Searches for messages belonging to a recipient
    public static String searchMessagesByRecipient(String recipient) {

        loadStoredMessages();

        String output = "";

        for (Message m : storedMessages) {

            if (m.recipient.equals(recipient)) {
                output += m.getDetails() + "\n\n";
            }
        }

        return output.isEmpty()
                ? "No messages found for this recipient."
                : output;
    }

    // Deletes a stored message using its hash
    public static String deleteMessageByHash(String hash) {

        loadStoredMessages();

        for (int i = 0; i < storedMessages.size(); i++) {

            Message m = storedMessages.get(i);

            if (m.messageHash.equals(hash)) {

                storedMessages.remove(i);

                saveStoredMessages();

                messageIDs.remove(m.messageID);
                messageHashes.remove(m.messageHash);

                return "Message \"" + m.messageText
                        + "\" successfully deleted.";
            }
        }

        return "Message hash not found.";
    }

    // Displays a complete report of stored messages
    public static String displayReport() {

        loadStoredMessages();

        String output = "";

        if (storedMessages.isEmpty()) {
            return "No stored messages found.";
        }

        output += "========== STORED MESSAGE REPORT ==========\n";

        for (Message m : storedMessages) {
            output += m.getDetails() + "\n"
                    + "Sender: " + m.sender + "\n"
                    + "-------------------------------------------\n";
        }

        return output;
    }

    // Returns message details
    public String getDetails() {

        return "Message ID: " + messageID + "\n"
                + "Message Hash: " + messageHash + "\n"
                + "Recipient: " + recipient + "\n"
                + "Message: " + messageText;
    }

    // Getters
    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }
    // Returns the sent messages
public static ArrayList<Message> getSentMessages() {
    return new ArrayList<>(sentMessages);
}

// Returns the message hashes
public static ArrayList<String> getMessageHashes() {
    return new ArrayList<>(messageHashes);
}

// Returns the message IDs
public static ArrayList<String> getMessageIDs() {
    return new ArrayList<>(messageIDs);
}


    // Returns the stored messages
    public static ArrayList<Message> getStoredMessages() {
        loadStoredMessages();
        return new ArrayList<>(storedMessages);
    }
    // Returns the disregarded messages
public static ArrayList<Message> getDisregardedMessages() {
    return new ArrayList<>(disregardedMessages);
}

    // Resets data before unit tests
    public static void resetForTests() {

        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();

        totalSent = 0;
        nextNumber = 0;
    }
  


}

