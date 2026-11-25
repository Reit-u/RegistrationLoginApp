/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Reitu
 */
package registrationloginapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Scanner;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;

/**
 *
 * @author Reitu
 */
public class ChatboxTest {
  
    private ChatboxTest;

    @BeforeEach
    public void setUp() {

    chatbox = new Chatbox();

    Arrays.fill(Chatbox.sentMessages, null);
    Arrays.fill(Chatbox.storedMessages, null);
    Arrays.fill(Chatbox.disregardedMessages, null);
    Arrays.fill(Chatbox.messageHashes, null);
    Arrays.fill(Chatbox.messageIDs, null);

    Arrays.fill(Chatbox.senders, null);
    Arrays.fill(Chatbox.recipients, null);
    Arrays.fill(Chatbox.messageStatus, null);
    Arrays.fill(Chatbox.allMessages, null);

    Chatbox.sentCount = 0;
    Chatbox.storedCount = 0;
    Chatbox.disregardedCount = 0;
    Chatbox.messageCount = 0;
}
}
   @Test
   public void testSentMessagesArray() {

    String input1 = "+27834557896\nDid you get the cake?\n1\n";

    Scanner scanner1 = new Scanner(new StringReader(input1));
    chatbox.sendMessage(scanner1);
    scanner1.close();

    String input2 = "0838884567\nIt is dinner time!\n1\n";

    Scanner scanner2 = new Scanner(new StringReader(input2));
    chatbox.sendMessage(scanner2);
    scanner2.close();

    assertEquals(
        Arrays.asList("Did you get the cake?", "It is dinner time!"),
        Arrays.asList(Arrays.copyOf(Chatbox.sentMessages, Chatbox.sentCount))
    );
}
@Test
public void testDisplayLongestMessage() {

    String longestMessage =
        "Where are you? You are late! I have asked you to be on time.";

    Chatbox.messageIDs[0] = "1234567890";
    Chatbox.messageHashes[0] = "12:1:WHERETIME";
    Chatbox.senders[0] = "Developer";
    Chatbox.recipients[0] = "+27838884567";
    Chatbox.allMessages[0] = longestMessage;
    Chatbox.messageStatus[0] = "Stored";

    Chatbox.storedMessages[0] = longestMessage;

    Chatbox.storedCount = 1;
    Chatbox.messageCount = 1;

    String output = captureOutput(() ->
        Chatbox.displayLongestMessage()
    );

    assertEquals(true, output.contains(longestMessage));
}
@Test
public void testSearchMessageID() {

    Chatbox.messageIDs[0] = "0838884567";
    Chatbox.messageHashes[0] = "08:1:ITTIMЕ";
    Chatbox.senders[0] = "Developer";
    Chatbox.recipients[0] = "0838884567";
    Chatbox.allMessages[0] = "It is dinner time!";
    Chatbox.messageStatus[0] = "Sent";

    Chatbox.sentMessages[0] = "It is dinner time!";

    Chatbox.sentCount = 1;
    Chatbox.messageCount = 1;

    String output = captureOutput(() -> {

        Scanner scanner = new Scanner("0838884567");

        Chatbox.searchMessageID(scanner);

        scanner.close();
    });

    assertEquals(true, output.contains("It is dinner time!"));
}
@Test
public void testSearchByRecipient() {

    // First message
    Chatbox.messageIDs[0] = "1234567890";
    Chatbox.messageHashes[0] = "12:1:WHERETIME";
    Chatbox.senders[0] = "Developer";
    Chatbox.recipients[0] = "+27838884567";

    Chatbox.allMessages[0] =
        "Where are you? You are late! I have asked you to be on time.";

    Chatbox.messageStatus[0] = "Stored";

    Chatbox.storedMessages[0] =
        Chatbox.allMessages[0];


    // Second message
    Chatbox.messageIDs[1] = "1234567891";
    Chatbox.messageHashes[1] = "12:2:OKYOU";

    Chatbox.senders[1] = "Developer";
    Chatbox.recipients[1] = "+27838884567";

    Chatbox.allMessages[1] =
        "Ok, I am leaving without you.";

    Chatbox.messageStatus[1] = "Sent";

    Chatbox.sentMessages[0] =
        Chatbox.allMessages[1];


    Chatbox.storedCount = 1;
    Chatbox.sentCount = 1;
    Chatbox.messageCount = 2;

    String output = captureOutput(() -> {

        Scanner scanner = new Scanner("+27838884567");

        Chatbox.searchByRecipient(scanner);

        scanner.close();
    });

    assertEquals(true, output.contains(
        "Where are you? You are late! I have asked you to be on time."
    ));

    assertEquals(true, output.contains(
        "Ok, I am leaving without you."
    ));
}
private String captureOutput(Runnable action) {

    PrintStream originalOutput = System.out;

    ByteArrayOutputStream output =
        new ByteArrayOutputStream();

    try (PrintStream testOutput = new PrintStream(output)) {

        System.setOut(testOutput);

        action.run();

    } finally {

        System.setOut(originalOutput);
    }

    return output.toString();
}
