/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package registrationloginapp;
import java.util.Scanner;
/**
 *
 * @author Reitu
 */
public class RegistrationLoginApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login user = new Login();
        System.out.print("Enter First Name: ");
        user.setFirstName(scanner.nextLine());
        
        System.out.print("Enter Last Name: ");
        user.setLastName(scanner.nextLine());
        
        String username= "";
        while(true){
            System.out.print("Enter Username(containing '_' and up to 5 characters): ");
            username= scanner.nextLine();
            if (user.checkUserName(username)){
                System.out.println("Username Verified");
            break;
            }
            else{ 
                System.out.println("Username Incorrectly Formatted");
            }
           }
        String password = "";
        while (true) {
        System.out.print("Enter Password (Password must be in caps contain special characters(e.g @) and numbers: ");
        password = scanner.nextLine();
        if (user.checkPasswordComplexity(password)) {
            System.out.println("Password verified");
            break;
        }
        else{
            System.out.println("Password is not correctly formatted");
          }
        }
        String cell = "";
        while (true) {
        System.out.println("Enter Cell Phone Number: ");
        cell = scanner.nextLine();
        
        if (user.checkCellPhoneNumber(cell)) {
            System.out.println("Cellphone Number Verified");
            break;
        }
        else{
            System.out.println("Cellphone number is incorrectly formatted");
           }
            
        }
        
       user.registerUser(username, password, cell);

       System.out.println("Registration Complete.");
       
       boolean loginStatus = false;
       
       while (!loginStatus){
           System.out.println("==LOGIN==");
           
           System.out.println("Enter Username: ");
           String loginUsername =  scanner.nextLine();
           
           System.out.println("Enter Password: ");
           String loginPassword = scanner.nextLine();
           
           loginStatus = user.loginUser(loginUsername, loginPassword);
           System.out.println(user.returnLoginStatus(loginStatus));
           if (!loginStatus){
               System.out.println("Please try again.");
           }
           
       }
       Chatbox chatbox = new Chatbox();
       chatbox.startChatbox(scanner);
       
       scanner.close();
    }
}

