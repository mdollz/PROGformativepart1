
package com.mycompany.progformativepart1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class MessagesTest {

    public MessagesTest() {
    }

    @BeforeEach
    public void setUp() {
        Messages.resetForTests();
    }

    @Test
    public void testCheckMessageID() {
        Messages message = new Messages(
            "1234567890", 0, "+27718693002", "Hello world"
        );

        assertTrue(message.checkMessageID());
    }

    @Test
    public void testCheckRecipientCell() {
        Messages validMessage = new Messages(
            "+27718693002", "Hello world"
        );

        assertEquals(
            "Cell phone number successfully captured.",
            validMessage.checkRecipientCell()
        );

        Messages invalidMessage = new Messages(
            "0718693002", "Hello world"
        );

        assertTrue(
            invalidMessage.checkRecipientCell()
                .contains("incorrectly formatted")
        );
    }

    @Test
    public void testCheckMessageLength() {
        Messages validMessage = new Messages(
            "+27718693002", "Hello world"
        );

        assertEquals(
            "Message ready to send.",
            validMessage.checkMessageLength()
        );

        String longText = "a".repeat(251);

        Messages longMessage = new Messages(
            "+27718693002", longText
        );

        assertTrue(
            longMessage.checkMessageLength()
                .contains("exceeds 250 characters by 1")
        );
    }

    @Test
    public void testCreateMessageHash() {
        Messages message = new Messages(
            "1234567890", 1, "+27718693002", "Hello world"
        );

        assertEquals(
            "12:1:HELLOWORLD",
            message.createMessageHash()
        );
    }

    @Test
    public void testMessageIDGenerated() {
        Messages message = new Messages(
            "1234567890", 0, "+27718693002", "Hello world"
        );

        assertEquals(
            "Message ID generated: 1234567890",
            message.messageIDGenerated()
        );
    }

    @Test
    public void testSentMessage() {
        Messages message = new Messages(
            "+27718693002", "Hello world"
        );

        assertEquals(
            "Message successfully sent.",
            message.sentMessage(1)
        );

        assertEquals(1, Messages.returnTotalMessages());

        assertEquals(
            "Press 0 to delete the message.",
            message.sentMessage(2)
        );

        assertEquals(
            "Message stored to send later.",
            message.sentMessage(3)
        );

        assertEquals(
            "Invalid option.",
            message.sentMessage(4)
        );
    }

    @Test
    public void testPrintMessages() {
        Messages message = new Messages(
            "+27718693002", "Hello world"
        );

        assertEquals(
            "No messages have been sent yet.",
            Messages.printMessages()
        );

        message.sentMessage(1);

        String history = Messages.printMessages();

        assertTrue(history.contains("Hello world"));
        assertTrue(history.contains("Message Hash:"));
        assertTrue(history.contains("+27718693002"));
    }

    @Test
    public void testReturnTotalMessages() {
        assertEquals(0, Messages.returnTotalMessages());

        Messages first = new Messages(
            "+27718693002", "First message"
        );

        first.sentMessage(1);

        assertEquals(1, Messages.returnTotalMessages());

        Messages second = new Messages(
            "+27718693003", "Second message"
        );

        second.sentMessage(1);

        assertEquals(2, Messages.returnTotalMessages());
    }

    @Test
    public void testStoreMessage() {
        File file = new File("storedMessages.json");

        if (file.exists()) {
            assertTrue(file.delete(),
                "Could not remove the previous test file.");
        }

        Messages message = new Messages(
            "1234567890", 0, "+27718693002", "Stored test message"
        );

        message.storeMessage();

        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    public void testGetDetails() {
        Messages message = new Messages(
            "1234567890", 0, "+27718693002", "Hello world"
        );

        String details = message.getDetails();

        assertTrue(details.contains("Message ID: 1234567890"));
        assertTrue(details.contains("Message Hash: 12:0:HELLOWORLD"));
        assertTrue(details.contains("Recipient: +27718693002"));
        assertTrue(details.contains("Message: Hello world"));
    }

    @Test
    public void testGetMessageID() {
        Messages message = new Messages(
            "1234567890", 0, "+27718693002", "Hello world"
        );

        assertEquals("1234567890", message.getMessageID());
    }

    @Test
    public void testGetMessageHash() {
        Messages message = new Messages(
            "1234567890", 0, "+27718693002", "Hello world"
        );

        assertEquals(
            "12:0:HELLOWORLD",
            message.getMessageHash()
        );
    }

    @Test
    public void testResetForTests() {
        Messages message = new Messages(
            "+27718693002", "Hello world"
        );

        message.sentMessage(1);

        assertEquals(1, Messages.returnTotalMessages());

        Messages.resetForTests();

        assertEquals(0, Messages.returnTotalMessages());
        assertEquals(
            "No messages have been sent yet.",
            Messages.printMessages()
        );
    }
}