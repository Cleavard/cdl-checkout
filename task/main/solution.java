package task.main;

import java.util.Scanner;

public class solution{
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        

        while (true) {
            //read user input
            String userInput = scanner.nextLine();
            
            if (userInput.equals("exit")){
                break;
            }
        }
        scanner.close();
    }

}