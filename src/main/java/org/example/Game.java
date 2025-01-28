package org.example;

import org.example.droplets.console;
import org.example.droplets.prize;

public class Game {
    private int points;

    /**
     * It plays a game with the user and returns the amount of money they have won
     */
    public int play() {
        while (points <= 15) {
            console.paragraph();

            Question question = new Question(points + 1);
            console.println(question.GetQuestion());
            displayAnswers(question);

            int correctAnswer = question.GetCorrectAnswer();
            String userInput = console.readline("Answer: ");

            try {
                int userAnswer = Integer.parseInt(userInput);

                if (userAnswer == correctAnswer) {
                    points++;
                    handleCorrectAnswer();
                    if (!promptNextStep()) {
                        return generatePrize();
                    }
                } else {
                    handleWrongAnswer(correctAnswer);
                    return 0;
                }
            } catch (NumberFormatException e) {
                console.paragraph();
                console.println("Invalid input. Please enter a number.");
                return 0;
            }
        }

        console.println("Congratulations! You won one million dollars!");
        return generatePrize();
    }






    private void displayAnswers(Question question) {
        String[] answers = question.GetAnswers();
        for (int i = 0; i < answers.length; i++) {
            console.println((i + 1) + ". " + answers[i]);
        }
    }

    private void handleCorrectAnswer() {
        console.paragraph();
        console.println("Correct!");
        console.println("Points: " + points);
        console.paragraph();
    }

    private boolean promptNextStep() {
        console.println("1. Next question");
        console.println("2. Payout");

        String choice = console.readline("Your choice: ");
        if ("2".equals(choice)) {
            return false;
        } else if (!"1".equals(choice)) {
            console.println("Invalid choice, continuing to next question.");
        }
        return true;
    }

    private void handleWrongAnswer(int correctAnswer) {
        console.println("Correct Answer: " + correctAnswer);
        console.println("Wrong! Game over.");
    }

    private int generatePrize() {
        prize winnings = new prize(points);
        return winnings.generate();
    }
}
