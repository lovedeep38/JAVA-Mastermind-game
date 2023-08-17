// Purpose:  This program will play a mastermind game where user enter a number and he have to guess the right number and find his average performance of total number of games
//

import java.util.Scanner;
import java.util.Arrays;
import static java.lang.Character.isDigit;

/**
 * This class contains all methods that are required to play mastermind
 * @author Lovedeep Singh
 * @since 7 April 2022
 */
public class MasterMind {
    
    private static Scanner keyboard = new Scanner(System.in);
    
    /**
     * This method call other method and run the program
     * @param args Sequence of characters passed between the program
     */
    public static void main(String[] args) {
        
        printIdentification();
        game();
    }
     
    /**Printing my identification */   
    private static void printIdentification() {
        System.out.println("Name: Lovedeep Singh");
        System.out.println("Mastermind Game");
        System.out.println();
    }
    
    /**This method plays whole mastermind game using other methods*/
    private static void game() {
        String command = "";        //this is used to play next game or end the game
        int numberOfGames = 0;      //this is the total number of games played by the user
        double totalGuesses = 0;    //this is the total number of guesses in total number of games played by the user

        System.out.println("Game 1 starts ");
        
        while(!command.equals("STOP")) {
            numberOfGames++;
            
            System.out.println("Enter the total number of digit you want to guess(not more than 10 but at least 2)");
            int size = keyboard.nextInt();
        
            while(size < 2 || size >= 10) {
                System.out.println("Error: Please enter size not more than 10 but at least 2");
                size = keyboard.nextInt();
            }
            
            int[] numberFromComputer = randomNumber(size);      //calling computer generated number
        
            System.out.print("Computer generated number is: ");
            int num = 1;
            while(size >= num) {
                System.out.print("*");
                num++;
            }
            System.out.println();
        
        
            int numberOfGuess = 1;              //this is the total number of guess in a game
            while(numberOfGuess <= 10) {
                System.out.println("Guess a number and every number should be different or enter 'on' to get the number: ");
                String number = keyboard.next();
                
                if(number.equals("on")) {
                    System.out.print("The number is: ");
                    printRandomToString(numberFromComputer);
                    break;
                }
                
                String guessed = guessedNumber(number,size);
                
                while(!checkRepetition(guessed)) {
                    System.out.println("Do not repeat a number more than one time. Try again.");
                    System.out.println("Guess a number and every number should be different: ");
                    number = keyboard.next();
                
                    guessed = guessedNumber(number,size);
                    
                }
                
                int[] guessedArray = guessedNumberToArray(guessed,size);
            
                if(Arrays.equals(guessedArray,numberFromComputer) ) {
                    System.out.println("Good Game, Your guess is right.");
                    System.out.println();
                    break;
                
                }
                else {
                    System.out.println("---------------------------------------------------------");
                    int correctDigits = numberOfCorrectDigits(guessedArray,numberFromComputer);
                    System.out.println("Number of correct digits: " + correctDigits);
        
                    int rightPlace = DigitsInRightPlace(guessedArray, numberFromComputer);
                    System.out.println("Number of digits in right place: " + rightPlace);
        
                    int wrongPlace = DigitsNotInRightPlace(correctDigits,rightPlace);
                    System.out.println("Number of digits in wrong place: " + wrongPlace);
                    
                    System.out.println("Number of guess: "+ numberOfGuess);
                    System.out.println("---------------------------------------------------------");
                    System.out.println();
                    
                    if(numberOfGuess == 10) {
                        System.out.println("Sorry, you take total 10 guess but unfortunately you didn't find a number.");
                        System.out.print("The number is: ");
                        printRandomToString(numberFromComputer);
                        System.out.println();
                    }
                    hints(numberOfGuess,numberFromComputer);
                    
                    numberOfGuess++;
                    
                    
                }
                
            }
            System.out.println("Game " + numberOfGames + " complete.");
            totalGuesses += numberOfGuess;
            System.out.println();
            System.out.println("Enter anything to start next game or enter 'stop' to end the game." );
            command = keyboard.next().toUpperCase();
        }

        double average = totalGuesses/numberOfGames;
        System.out.print("Your average performance is ");
        System.out.printf("%.2f", average);
        System.out.println();
        System.out.println("Thanks for playing.");
    }
    
    /**This method generate a random number of inputted size
     * @param size This is a total number of digits guessed 
     * @return the random number in an array
     */
    private static int[] randomNumber(int size) {
        int random = 0;
        int[] array = new int[size];
        for(int index = 0; index < array.length;index++) {
            random =  (int)(Math.random()*10);

            while(!checkSameDigit(array,random)){
                random =  (int)(Math.random()*10);
            }
            array[index] = random;
        }
        return array;
    }
    
    /**This method checks if the computer generated number contains duplicate number
     * @param array this is random numbers in an array
     * @param random this is random number 
     * @return true or false
     */
    private static boolean checkSameDigit(int[] array,int random) {
        for(int index = 0; index < array.length; index++) {
            if(array[index] == random) {
                return false;
            }
        }
        return true;
    }
    
    /**This number prints computer generated number
     */
    private static void printRandomToString(int[] numberFromComputer)
    {
        for(int element:numberFromComputer)
        {
            System.out.print(element+"");
        }
        System.out.println("");
    }
    
    /**This method checks the guessed number inputted by the user contains only digits and size is correct or not
     * @param number this is the guessed number inputted by the user
     * @param size This is a total number of digits guessed
     * @return the guessed number
     */
    private static String guessedNumber(String number, int size) {
        
        int index = 0;
        while(index < number.length()) {
            while(!isDigit(number.charAt(index)) ) {
                System.out.print("Please enter only digits of inputted size: ");
                number = keyboard.next();
                index = 0;
            }
            
            while(number.length() != size) {
                System.out.print("Please enter a number of inputted size: ");
                number = keyboard.next();
            }
            index++;
        }
        return number;
    }
    
    /**This method checks the guessed number entered by the user contains unique number or not
     * @param guessed this is the number guessed by the user
     * @return true or false
     */
    private static boolean checkRepetition(String guessed) {
        for(int i = 0; i < guessed.length();i++) {
            for(int index = i + 1; index < guessed.length(); index++) {
                if(guessed.charAt(i) == guessed.charAt(index)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**This method converts guessed number to array
     * @param guessed this is the number guessed by the user
     * @param size This is a total number of digits guessed
     * @return the array of guessed number
     */
    private static int[] guessedNumberToArray(String guessed, int size) {
        int guessedNumber = Integer.parseInt(guessed);
        int[] guessedNumberArray = new int[size];
        int lastIndex = guessedNumberArray.length-1;
        int digit = 0;
        
        while (guessedNumber > 0 && lastIndex >=0)
        {
            digit = guessedNumber % 10;
            guessedNumberArray[lastIndex]=digit;
            lastIndex--;
            guessedNumber=guessedNumber/10;
        }
        return guessedNumberArray;
        
    }
    
    /**This method checks the correct number of digits in a guessed number
     * @param guessedArray this is the array of number guessed by the user
     * @param numberFromComputer this is the array of computer generated number
     * @return the total number of correct digits
     */
    private static int numberOfCorrectDigits(int[] guessedArray, int[] numberFromComputer) {
        int correctNumber = 0;
        for(int computerNumIndex = 0; computerNumIndex < numberFromComputer.length; computerNumIndex++) {
            for(int index = 0; index < guessedArray.length; index++) {
                if(numberFromComputer[computerNumIndex] == guessedArray[index]) {
                    correctNumber++;
                }
            }
        }
        return correctNumber;
        
    }
    
    /**This method checks the total number of digits that are in their right in a guessed number
     * @param guessedArray this is the array of number guessed by the user
     * @param numberFromComputer this is the array of computer generated number
     * @return the total number of digits in their right place
     */
    private static int DigitsInRightPlace(int[] guessedArray, int[] numberFromComputer) {
        int rightPlace = 0;
        int index = 0;
        while(index < guessedArray.length) {
            if(guessedArray[index] == numberFromComputer[index]) {
                rightPlace++;
            }
            index++;
        }
        return rightPlace;
    }
    
    /**This method checks the total number of digits are correct but not in their right place
     * @param correctDigits this is the total number of digits that are correct
     * @param rightPlace this is the total number of digits in their right place
     * @return the total number of digits are correct but not in their right place
     */
    private static int DigitsNotInRightPlace(int correctDigits, int rightPlace) {
        int wrongPlace =  correctDigits - rightPlace;
        return wrongPlace;
    }
    
    /**This methods gives hint to the player after some wrong guesses
     * @param numberOfGuess this is the total number of guesses
     * @param numberFromComputer this is the number guessed by the user
     */
    private static void hints(int numberOfGuess, int[] numberFromComputer) {
        int totalLength = numberFromComputer.length;
        int index = 0;
        if(numberOfGuess == 3) {
            System.out.print("Here is one hint for you: ");
            while(index < totalLength) {
                if(index == totalLength/2) {
                    System.out.print(numberFromComputer[totalLength/2]);
                }
                else{
                    System.out.print("*");
                }
                index++;
            }
        }
        else if(numberOfGuess == 6) {
            System.out.print("Here is one more hint for you: ");
            while(index < totalLength) {
                if(index == totalLength/2) {
                    System.out.print(numberFromComputer[totalLength/2]);
                }
                else if(index == totalLength/4) {
                    System.out.print(numberFromComputer[totalLength/4]);
                }
                else{
                    System.out.print("*");
                }
                index++;
            }
        }
        else if(numberOfGuess == 9) {
            System.out.print("This is the last hint for you: ");
            while(index < totalLength) {
                if(index == totalLength/2) {
                    System.out.print(numberFromComputer[totalLength/2]);
                }
                else if(index == totalLength/4) {
                    System.out.print(numberFromComputer[totalLength/4]);
                }
                else if(index == totalLength/6) {
                    System.out.print(numberFromComputer[totalLength/6]);
                }
                else{
                    System.out.print("*");
                }
                index++;
            }
        }
        System.out.println();
    }
}



