/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package registrationloginapp;

import java.util.*;
import java.util.regex.*;
import java.io.*;

public class Chatbox {

    private int totalMessages = 0;

    // Message category arrays required by the assignment
    static String[] sentMessages = new String[100];
    static String[] storedMessages = new String[100];
    static String[] disregardedMessages = new String[100];
    static String[] messageHashes = new String[100];
    static String[] messageIDs = new String[100];

    // Supporting parallel arrays. These use the same index for each message record.
    static String[] senders = new String[100];
    static String[] recipients = new String[100];
    static String[] messageStatus = new String[100];
    static String[] allMessages = new String[100];

    // Category and record counters
    static int sentCount = 0;
    static int disregardedCount = 0;
    static int storedCount = 0;
    static int messageCount = 0;

    
    public void startChatbox(Scanner input) {

    int option = 0;

    System.out.println("\nWelcome to QuickChat!");

    while (option != 4) {

        System.out.println("\n== QUICKCHAT MENU ==");
        System.out.println("1. Send Message");
        System.out.println("2. View Messages");
        System.out.println("3. Display Total Messages");
        System.out.println("4. Exit");

        System.out.print("Choose an option: ");

        String choice = input.nextLine();

        try {
            option = Integer.parseInt(choice);
        }
        catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            continue;
        }

        if (option == 1) {

            System.out.print("How many messages would you like to enter? ");

            int numberOfMessages = Integer.parseInt(input.nextLine());

            for (int i = 0; i < numberOfMessages; i++) {

                System.out.println("\nEntering message " + (i + 1));

                sendMessage(input);
            }
        }

        else if (option == 2) {

            System.out.println(printMessages());

        }

        else if (option == 3) {

            System.out.println("Total messages sent: " + returnTotalMessages());

        }

        else if (option == 4) {

            System.out.println("Thank you for using QuickChat. Goodbye!");

        }

        else {

            System.out.println("Invalid option. Please choose between 1 and 4.");

        }
    }
}

    private static int readMessageLimit(Scanner input) {
        while (true) {
            System.out.print("How many messages would you like to enter? ");
            String value = input.nextLine();
            try {
                int number = Integer.parseInt(value);
                if (number >= 0 && number <= 100) {
                    return number;
                }
                System.out.println("Please enter a number between 0 and 100.");
            } 
            catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    public static void storedMessagesMenu(Scanner input) {
        int option = 0;

        // Keep the submenu open until the user selects option 7.
        while (option != 7) {
            System.out.println("\n=== Stored Messages ===");
            System.out.println("1. Display senders and recipients");
            System.out.println("2. Display longest message");
            System.out.println("3. Search by message ID");
            System.out.println("4. Search by recipient");
            System.out.println("5. Delete a message");
            System.out.println("6. Display message report");
            
            System.out.println("7. Return to main menu");
            System.out.print("Choose an option: ");
            String value = input.nextLine();
            try {
                option = Integer.parseInt(value);
            } 
            catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            if (option == 1) {
                displaySendersAndRecipients();
            }
            else if (option == 2) {
                displayLongestMessage();
            }
            else if (option == 3) {
                searchMessageID(input);
            } 
            else if (option == 4) {
                searchByRecipient(input);
            } 
            else if (option == 5) {
                deleteMessage(input);
            } 
            else if (option == 6) {
                displayReport();
            } 
            else if (option == 7) {
                System.out.println("Returning to main menu.");
            } 
            else {
                System.out.println("Invalid option. Please choose between 1 and 7.");
            }
      }
    }

    public static void displaySendersAndRecipients() {
        System.out.println("\n=== Senders and Recipients ===");
        boolean found = false;

        for (int i = 0; i < messageCount; i++) {
            if ("Stored".equals(messageStatus[i])) {
                System.out.println("Sender: " + senders[i]);
                System.out.println("Recipient: " + recipients[i]);
                System.out.println("--------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No stored messages available.");
        }
    }

    public static void displayLongestMessage() {
        int longestIndex = -1;

        for (int i = 0; i < messageCount; i++) {
            if ("Stored".equals(messageStatus[i]) && allMessages[i] != null) {
            if (longestIndex == -1 || allMessages[i].length() > allMessages[longestIndex].length()) {
                    longestIndex = i;
            }
            }
        }

        if (longestIndex == -1) {
            System.out.println("No stored messages available.");
        } 
        else {
            System.out.println("Longest Message: " + allMessages[longestIndex]);
            System.out.println("Number of characters: " + allMessages[longestIndex].length());
        }
    }

    public static void searchMessageID(Scanner input) {
        System.out.print("Enter message ID: ");
        String searchID = input.nextLine();
        boolean found = false;

        for (int i = 0; i < messageCount; i++) {
            if (messageIDs[i] != null && messageIDs[i].equals(searchID)
                    && !"Deleted".equals(messageStatus[i])) {
                System.out.println("Message found!");
                System.out.println("Recipient: " + recipients[i]);
                System.out.println("Message: " + getMessage(i));
                found = true;
                break;
        }
        }

        if (!found) {
            System.out.println("Message ID not found.");
        }
    }

    public static String getMessage(int index) {
        if (index < 0 || index >= messageCount) {
            return null;
        }
        return allMessages[index];
    }

    public static void searchByRecipient(Scanner input) {
        System.out.print("Enter recipient number: ");
        String searchRecipient = input.nextLine();
        boolean found = false;

        for (int i = 0; i < messageCount; i++) {
            if (recipients[i] != null && recipients[i].equals(searchRecipient) && ("Sent".equals(messageStatus[i]) || "Stored".equals(messageStatus[i]))) {
                System.out.println("Message found:");
                System.out.println(getMessage(i));
                found = true;
            }
        }

        if (!found) {
            System.out.println("No messages found for this recipient.");
        }
    }

    public static void deleteMessage(Scanner input) {
        System.out.print("Enter Message Hash: ");
        String searchHash = input.nextLine();
        boolean found = false;

        for (int i = 0; i < messageCount; i++) {
            if (messageHashes[i] != null && messageHashes[i].equals(searchHash)
                    && !"Deleted".equals(messageStatus[i])) {
                String status = messageStatus[i];
                String message = allMessages[i];
                System.out.println("Message: " + message);

                if ("Stored".equals(status)) {
                    storedCount = removeMessageFromArray(storedMessages, storedCount, message);
                } else if ("Sent".equals(status)) {
                    sentCount = removeMessageFromArray(sentMessages, sentCount, message);
                    // totalMessages is the number of messages currently marked as sent.
                } else if ("Disregarded".equals(status)) {
                    disregardedCount = removeMessageFromArray(disregardedMessages, disregardedCount, message);
                }

                messageStatus[i] = "Deleted";
                messageHashes[i] = null;
                messageIDs[i] = null;
                recipients[i] = null;
                senders[i] = null;
                allMessages[i] = null;

                
              if ("Stored".equals(status)) {
                    System.out.println(storeAllStoredMessages());
                }

                System.out.println("Message successfully deleted.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Message hash not found.");
        }
    }

    private static int removeMessageFromArray(String[] array, int count, String message) {
        for (int i = 0; i < count; i++) {
         if (message.equals(array[i])) {
                
             for (int j = i; j < count - 1; j++) {
                    array[j] = array[j + 1];
                }
                array[count - 1] = null;
                return count - 1;
            }
        }
        return count;
    }

    public static void displayReport() {
        System.out.println("\n=== Message Report ===");
        boolean found = false;

        for (int i = 0; i < messageCount; i++) {
            if ("Sent".equals(messageStatus[i])) {
                System.out.println("Message ID: " + messageIDs[i]);
                System.out.println("Message Hash: " + messageHashes[i]);
                System.out.println("Recipient: " + recipients[i]);
                System.out.println("Message: " + allMessages[i]);
                System.out.println("--------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No sent messages available.");
        }
    }

    public static String generateMessageID() {
        Random random = new Random();
        long id = 1000000000L + (long) (random.nextDouble() * 9000000000L);
        return String.valueOf(id);
    }

    public boolean checkMessageID(String messageID) {
        return messageID != null && messageID.matches("\\d{10}");
    }

    public boolean checkRecipientPhoneNumber(String cellPhoneNumber) {
        if (cellPhoneNumber == null) {
            return false;
        }
        String regex = "^\\+?\\d{10,12}$";
        return Pattern.matches(regex, cellPhoneNumber);
    }

    public String createMessageHash(String messageID, int messageNumber, String message) {
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0].replaceAll("^[^A-Za-z0-9]+|[^A-Za-z0-9]+$", "");
        String lastWord = words[words.length - 1].replaceAll("^[^A-Za-z0-9]+|[^A-Za-z0-9]+$", "");
        return (messageID.substring(0, 2) + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    public void sendMessage(Scanner input) {
        if (messageCount >= messageIDs.length) {
            System.out.println("The message limit has been reached.");
            return;
        }

        String recipient;
        while (true) {
            System.out.print("Enter recipient cellphone number: ");
            recipient = input.nextLine();
            if (checkRecipientPhoneNumber(recipient)) {
                break;
            }
            System.out.println("Invalid cellphone number. Please include the international code where required.");
        }

        String message;
        while (true) {
            System.out.print("Enter your message (maximum 250 characters): ");
            message = input.nextLine();
            if (message.trim().isEmpty()) {
                System.out.println("Your message cannot be empty.");
            } else if (message.length() > 250) {
                System.out.println("Please enter a message of less than 250 characters.");
            } else {
                break;
            }
        }

        String messageID = generateMessageID();
        while (!checkMessageID(messageID)) {
            messageID = generateMessageID();
        }
        String messageHash = createMessageHash(messageID, messageCount, message);

        System.out.println("Message ID: " + messageID);
        System.out.println("Message: " + message);
        System.out.println("Message Hash: " + messageHash);

        String messageOption;
        while (true) {
            System.out.println("\nWhat would you like to do with your message?");
            System.out.println("1. Send Message");
            System.out.println("2. Disregard Message");
            System.out.println("3. Store Message to Send Later");
            System.out.print("Choose an option: ");
            messageOption = input.nextLine();
            if (messageOption.equals("1") || messageOption.equals("2") || messageOption.equals("3")) {
                break;
            }
            System.out.println("Invalid option. Please choose 1, 2 or 3.");
        }

        int index = messageCount;
        messageIDs[index] = messageID;
        messageHashes[index] = messageHash;
        senders[index] = "Developer";
        recipients[index] = recipient;
        allMessages[index] = message;

        if (messageOption.equals("1")) {
            sentMessages[sentCount] = message;
            sentCount++;
            messageStatus[index] = "Sent";
            totalMessages++;
            System.out.println("Message sent successfully!");
        } else if (messageOption.equals("2")) {
            disregardedMessages[disregardedCount] = message;
            disregardedCount++;
            messageStatus[index] = "Disregarded";
            System.out.println("Message disregarded.");
        } else if (messageOption.equals("3")) {
            storedMessages[storedCount] = message;
            storedCount++;
            messageStatus[index] = "Stored";
            messageCount++;
            System.out.println(storeMessage(messageID, recipient, message, messageHash));
            return;
        }

        messageCount++;
    }

    public String storeMessage(String messageID, String recipient, String message, String messageHash) {
        return storeAllStoredMessages();
    }

    private static String storeAllStoredMessages() {
        try (FileWriter writer = new FileWriter("messages.json", false)) {
            writer.write("[\n");
            boolean first = true;

            for (int i = 0; i < messageCount; i++) {
                if ("Stored".equals(messageStatus[i]) && allMessages[i] != null) {
                    if (!first) {
                        writer.write(",\n");
                    }
                    writer.write("  {\"messageID\":\"" + escapeJson(messageIDs[i]) + "\",");
                    writer.write("\"sender\":\"" + escapeJson(senders[i]) + "\",");
                    writer.write("\"recipient\":\"" + escapeJson(recipients[i]) + "\",");
                    writer.write("\"message\":\"" + escapeJson(allMessages[i]) + "\",");
                    writer.write("\"messageHash\":\"" + escapeJson(messageHashes[i]) + "\"}");
                    first = false;
                }
            }

            writer.write("\n]\n");
            return "Message successfully stored.";
        } catch (IOException e) {
            return "Error storing message: " + e.getMessage();
        }
    }

    private static String escapeJson(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    public static void loadStoredMessagesFromJSON() {
        File file = new File("messages.json");
        if (!file.exists()) {
            return;
        }

        StringBuilder json = new StringBuilder();
        try (Scanner fileInput = new Scanner(file)) {
            while (fileInput.hasNextLine()) {
                json.append(fileInput.nextLine()).append('\n');
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not read messages.json: " + e.getMessage());
            return;
        }

        ArrayList<String> objects = splitJsonObjects(json.toString());
        for (String object : objects) {
            if (messageCount >= messageIDs.length) {
                System.out.println("Message capacity reached while loading JSON.");
                break;
            }

            String id = extractJsonString(object, "messageID");
            String sender = extractJsonString(object, "sender");
            String recipient = extractJsonString(object, "recipient");
            String message = extractJsonString(object, "message");
            String hash = extractJsonString(object, "messageHash");

            if (id != null && recipient != null && message != null && hash != null) {
                int index = messageCount;
                messageIDs[index] = id;
                messageHashes[index] = hash;
                senders[index] = sender == null ? "Developer" : sender;
                recipients[index] = recipient;
                allMessages[index] = message;
                messageStatus[index] = "Stored";
                storedMessages[storedCount] = message;
                storedCount++;
                messageCount++;
            }
        }
    }

    private static ArrayList<String> splitJsonObjects(String json) {
        ArrayList<String> objects = new ArrayList<>();
        int depth = 0;
        int start = -1;
        boolean inString = false;
        boolean escaped = false;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (inString) {
                if (escaped) {
                    escaped = false;
                } else if (c == '\\') {
                    escaped = true;
                } else if (c == '"') {
                    inString = false;
                }
            } else {
                if (c == '"') {
                    inString = true;
                } else if (c == '{') {
                    if (depth == 0) {
                        start = i;
                    }
                    depth++;
                } else if (c == '}') {
                    depth--;
                    if (depth == 0 && start >= 0) {
                        objects.add(json.substring(start, i + 1));
                        start = -1;
                    }
                }
            }
        }
        return objects;
    }

    private static String extractJsonString(String object, String key) {
        String expression = "\"" + Pattern.quote(key)
                + "\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"";
        Matcher matcher = Pattern.compile(expression).matcher(object);
        if (matcher.find()) {
            return unescapeJson(matcher.group(1));
        }
        return null;
    }

    private static String unescapeJson(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\\' && i + 1 < text.length()) {
                char next = text.charAt(++i);
                if (next == 'n') {
                    result.append('\n');
                } else if (next == 'r') {
                    result.append('\r');
                } else if (next == 't') {
                    result.append('\t');
                } else if (next == '"' || next == '\\' || next == '/') {
                    result.append(next);
                } else {
                    result.append(next);
                }
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public String printMessages() {
        if (sentCount == 0) {
            return "No messages yet.";
        }

        StringBuilder output = new StringBuilder("\n=== Sent Messages ===\n");
        for (int i = 0; i < messageCount; i++) {
            if ("Sent".equals(messageStatus[i])) {
                output.append("Recipient: ").append(recipients[i]).append('\n');
                output.append("Message: ").append(allMessages[i]).append('\n');
                output.append("--------------------\n");
            }
        }
        return output.toString();
    }

    public int returnTotalMessages() {
        return totalMessages;
    }
}


