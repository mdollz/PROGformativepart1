package com.mycompany.part2prog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class MessageTest {

    @BeforeEach
    public void setUp() throws IOException {
        Message.resetForTests();
        Files.deleteIfExists(Path.of("storedMessages.json"));
    }

    // PART 2 TESTS

    @Test
    public void testMessageLength() {
        Message message = new Message(
                "0012345678", 0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                "Message ready to send.",
                message.checkMessageLength()
        );
    }

    @Test
    public void testMessageLengthFailure() {
        String longMessage = "A".repeat(260);

        Message message = new Message(
                "0012345678", 0,
                "+27718693002",
                longMessage
        );

        assertTrue(message.checkMessageLength().contains("exceeds 250"));
    }

    @Test
    public void testRecipientNumber() {
        Message message = new Message(
                "0012345678", 0,
                "+27718693002",
                "Hello"
        );

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell()
        );
    }

    @Test
    public void testRecipientNumberFailure() {
        Message message = new Message(
                "0012345678", 0,
                "123456789",
                "Hello"
        );

        assertTrue(message.checkRecipientCell().contains("incorrectly formatted"));
    }

    @Test
    public void testMessageHash() {
        Message message = new Message(
                "0012345678", 0,
                "+27718693002",
                "Hi Mike"
        );

        assertNotNull(message.getMessageHash());
    }

    @Test
    public void testMessageIDGenerated() {
        Message message = new Message(
                "+27718693002",
                "Hello"
        );

        assertNotNull(message.getMessageID());
        assertEquals(10, message.getMessageID().length());
    }

    @Test
    public void testSendMessageOptions() {
        Message message = new Message(
                "+27718693002",
                "Hello"
        );

        assertEquals(
                "Press 0 to delete the message.",
                message.sentMessage(2)
        );
    }

    @Test
    public void testTotalMessagesSent() {
        Message message = new Message(
                "+27718693002",
                "Hello"
        );

        message.sentMessage(1);

        assertEquals(1, Message.returnTotalMessages());
    }

    // PART 3 TESTS

    @Test
    public void testSentMessagesArray() {
        Message message1 = new Message(
                "0838845670",
                "0012345678", 0,
                "+27834557896",
                "Did you get the cake?"
        );

        Message message2 = new Message(
                "0838845670",
                "0838884567", 3,
                "+27838884567",
                "It is dinner time!"
        );

        message1.sentMessage(1);
        message2.sentMessage(1);

        String expected = message1.getDetails() + "\n"
                + message2.getDetails() + "\n";

        assertEquals(expected, Message.printMessages());
        assertEquals(2, Message.returnTotalMessages());
    }

    @Test
    public void testLongestMessage() {
        Message message = new Message(
                "0838845670",
                "0012345679", 1,
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time."
        );

        message.sentMessage(3);

        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                Message.displayLongestMessage()
        );
    }

    @Test
    public void testSearchMessageByID() {
        Message message = new Message(
                "0838845670",
                "0838884567", 3,
                "+27838884567",
                "It is dinner time!"
        );

        message.sentMessage(3);

        String expected = "Recipient: +27838884567"
                + "\nMessage: It is dinner time!";

        assertEquals(
                expected,
                Message.searchMessageByID("0838884567")
        );
    }

    @Test
    public void testSearchMessagesByRecipient() {
        Message message1 = new Message(
                "0838845670",
                "0012345679", 1,
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time."
        );

        Message message2 = new Message(
                "0838845670",
                "0012345682", 4,
                "+27838884567",
                "Ok, I am leaving without you."
        );

        message1.sentMessage(3);
        message2.sentMessage(3);

        String expected = message1.getDetails() + "\n\n"
                + message2.getDetails() + "\n\n";

        assertEquals(
                expected,
                Message.searchMessagesByRecipient("+27838884567")
        );
    }

    @Test
    public void testDeleteMessageByHash() {
        Message message = new Message(
                "0838845670",
                "0012345679", 1,
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time."
        );

        message.sentMessage(3);

        String expected = "Message \"Where are you? You are late! "
                + "I have asked you to be on time.\" successfully deleted.";

        assertEquals(
                expected,
                Message.deleteMessageByHash(message.getMessageHash())
        );
    }

    @Test
    public void testDisplayReport() {
        Message message = new Message(
                "0838845670",
                "0012345678", 0,
                "+27834557896",
                "Did you get the cake?"
        );

        message.sentMessage(3);

        String expected = "========== STORED MESSAGE REPORT ==========\n"
                + message.getDetails() + "\n"
                + "Sender: 0838845670\n"
                + "-------------------------------------------\n";

        assertEquals(expected, Message.displayReport());
    }
    @Test
public void testDisregardedMessagesArray() {

    Message message = new Message(
            "0838845670",
            "0012345678", 0,
            "+27834557896",
            "Yohoooo, I am at your gate."
    );

    message.disregardMessage();

    assertEquals(
            1,
            Message.getDisregardedMessages().size()
    );
}

}

