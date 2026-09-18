/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.progformativepart1;
import java.util.Scanner;

/**
 *
 * @author Student
 */

public class PROGformativepart1 {
    //ask user for their details and also show registration

    public static void main(String[] args) {
         Scanner input = new Scanner(System.in);

        System.out.println("=== REGISTRATION ===");

        System.out.print("Enter your first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = input.nextLine();

        System.out.print("Enter a username: ");
        String username = input.nextLine();
        
        System.out.print("Enter a password: ");
        String password = input.nextLine();
        
        System.out.print("Enter your cell phone number: ");
        String cellphoneNumber = input.nextLine();

        // creating login object with the details the user typed in
        Login user = new Login(firstName, lastName, username, password, cellphoneNumber);

        //  registration message
        System.out.println();
        System.out.println(user.registerUser());

        //  cell phone message
        if (user.checkcellphoneNumber()) { 
            System.out.println("Cell phone number successfully added.");
        }else{
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
        }
        System.out.println();
        System.out.println("=== LOGIN ===");

        System.out.print("Enter your username: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter your password: ");
        String loginPassword = input.nextLine();

        System.out.println();
        System.out.println(user.returnLoginStatus(loginUsername, loginPassword));

        input.close();
    }
}
