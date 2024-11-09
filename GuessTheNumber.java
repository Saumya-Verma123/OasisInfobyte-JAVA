import java.util.Random;
import javax.swing.*;

public class GuessTheNumber {
    public static void main(String[] args) {
        Random random = new Random();
        int totalScore = 0;
        int rounds = 3; // Number of rounds to play
        boolean playAgain;
        do{
            for (int i = 0; i < rounds; i++) {
                int numberToGuess = random.nextInt(100) + 1;
                int numberOfTries = 0;
                int maxAttempts = 10; // Maximum attempts allowed
                boolean hasGuessedCorrectly = false;

                JOptionPane.showMessageDialog(null, "Round " + (i + 1) + ": Guess the number between 1 and 100! You have " + maxAttempts + " attempts.");

                while (numberOfTries < maxAttempts && !hasGuessedCorrectly) {
                    String input = JOptionPane.showInputDialog("Enter your guess:");
                    
                    // Validate input
                    if (input == null) {
                        JOptionPane.showMessageDialog(null, "Game cancelled.");
                        return;
                    }

                    int userGuess;
                    try {
                        userGuess = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                        continue;
                    }

                    numberOfTries++;

                    if (userGuess < numberToGuess) {
                        JOptionPane.showMessageDialog(null, "Too low! Try again.");
                    } else if (userGuess > numberToGuess) {
                        JOptionPane.showMessageDialog(null, "Too high! Try again.");
                    } else {
                        hasGuessedCorrectly = true;
                        JOptionPane.showMessageDialog(null, "Congratulations! You've guessed the number " + numberToGuess + " in " + numberOfTries + " attempts.");
                    }
                }

                if (!hasGuessedCorrectly) {
                    JOptionPane.showMessageDialog(null, "Sorry! You've run out of attempts. The number was " + numberToGuess + ".");
                }

                // Calculate score based on attempts
                int points = Math.max(0, maxAttempts - numberOfTries);
                totalScore += points;
                JOptionPane.showMessageDialog(null, "Your score for this round: " + points + ". Total score: " + totalScore);
            } 
            String response=JOptionPane.showInputDialog("Do you want to play again? (yes/no): ");
            playAgain = response.equalsIgnoreCase("yes");
        }while(playAgain);

        JOptionPane.showMessageDialog(null, "Game Over!");
        JOptionPane.showMessageDialog(null, "CONGRATULATIONS! Your total score: " + totalScore);
    }
}
