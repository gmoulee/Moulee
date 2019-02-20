package com.ma.quiz.main;

import java.util.Scanner;


// TODO: Auto-generated Javadoc
/**
 * The Class Launch.
 */
public class Launch{
	
	/**
	 * The main method.Launches to Quiz
	 *
	 * @param args the arguments
	 */
	//public static Scanner reader = new Scanner(System.in);
	public static void main(String args[]) {
		String user ="2";
		System.out.println("Welcome to Quiz Master");
		System.out.println("1. Admin    2.Student");
		  // Reading from System.in
		System.out.print("Enter a number: ");
		user = Test.readConsole();
		//user = reader.nextInt(); // Scans the next token of the input as an int.
		while(!user.equals("1") && !user.equals("2")) {
			System.out.println("Enter correct input"); //Checks and asks user to enter 1 or 2.
		}
		if(user.equals("1")) {
			System.out.println("Enter the password:");
			String password = Test.readConsole();
			String admin = Test.getAdminPassword();
			String[] temp = admin.split(":");
			admin = temp[1];
			if(password.equals(admin)) {
			Admin.displayAdmin();
			}
			else {
				System.out.println("Password wrong");
				Launch.main(null);
			}
		}
		else if(user.equals("2")) {
			Student.displayStudent();
		}
	}
	
	/*public static String getAnswer() {
		String answer = "";
		System.out.println("Enter your Answer:");
		answer = reader.nextLine();
		return answer;
	}*/
	
}