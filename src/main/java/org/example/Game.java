package org.example;

import org.example.droplets.console;
import org.example.droplets.prize;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private int points;
    private final List<String> availableJokers = new ArrayList<>();
    private boolean gameOver = false;

    public Game() {
        availableJokers.add("50:50");
        availableJokers.add("Audience");
        availableJokers.add("Phone");
        availableJokers.add("Switch");
    }

    public int play() {
        while (points <= 15 && !gameOver) {
            console.paragraph();
            Question question = new Question(points + 1);
            askQuestion(question);
        }
        if (gameOver) {
            return 0;
        }
        console.println("Congratulations! You won one million dollars!");
        return generatePrize();
    }

    private void askQuestion(Question question) {
        console.println(question.GetQuestion());
        displayAnswers(question);
        displayJokers();

        int correctAnswer = question.GetCorrectAnswer();
        boolean answered = false;

        while (!answered && !gameOver) {
            String userInput = console.readline("Answer (or type 'joker' to use one): ");
            if ("joker".equalsIgnoreCase(userInput)) {
                useJoker(question);
            } else {
                answered = checkAnswer(userInput, correctAnswer);
            }
        }
    }

    private boolean checkAnswer(String userInput, int correctAnswer) {
        try {
            int userAnswer = Integer.parseInt(userInput);
            if (userAnswer == correctAnswer) {
                points++;
                handleCorrectAnswer();
                return promptNextStep();
            } else {
                handleWrongAnswer(correctAnswer);
                gameOver = true;
                return true;
            }
        } catch (NumberFormatException e) {
            console.paragraph();
            console.println("Invalid input. Please enter a number.");
            return false;
        }
    }

    private void displayAnswers(Question question) {
        String[] answers = question.GetAnswers();
        for (int i = 0; i < answers.length; i++) {
            console.println((i + 1) + ". " + answers[i]);
        }
    }

    private void displayJokers() {
        if (!availableJokers.isEmpty()) {
            console.println("Available Jokers: " + String.join(", ", availableJokers));
        }
    }

    private void useJoker(Question question) {
        console.println("Choose a Joker: " + String.join(", ", availableJokers));
        String choice = console.readline("Joker: ");
        if (!availableJokers.contains(choice)) {
            console.println("Invalid Joker");
            return;
        }
        availableJokers.remove(choice);
        switch (choice) {
            case "50:50":
                use5050Joker(question);
                break;
            case "Audience":
                useAudienceJoker(question);
                break;
            case "Phone":
                usePhoneJoker(question);
                break;
            case "Switch":
                askQuestion(new Question(points + 1));
                break;
        }
    }

    private void use5050Joker(Question question) {
        console.println("Using 50:50 Joker...");
        String[] answers = question.GetAnswers();
        int correctAnswer = question.GetCorrectAnswer();
        List<Integer> wrongAnswers = new ArrayList<>();
        for (int i = 0; i < answers.length; i++) {
            if (i + 1 != correctAnswer) {
                wrongAnswers.add(i + 1);
            }
        }
        Random rand = new Random();
        wrongAnswers.remove(rand.nextInt(wrongAnswers.size()));
        console.println("Remaining options: " + correctAnswer + " and " + wrongAnswers.get(0));
    }

    private void useAudienceJoker(Question question) {
        console.println("Using Audience Joker...");
        Random rand = new Random();
        int correctAnswer = question.GetCorrectAnswer();
        int[] percentages = new int[4];
        percentages[correctAnswer - 1] = rand.nextInt(40) + 50;
        for (int i = 0; i < 4; i++) {
            if (i != correctAnswer - 1) {
                percentages[i] = rand.nextInt(20);
            }
        }
        console.println("Audience Poll: 1: " + percentages[0] + "% | 2: " + percentages[1] + "% | 3: " + percentages[2] + "% | 4: " + percentages[3] + "%");
    }

    private void usePhoneJoker(Question question) {
        console.println("Using Phone Joker...");
        int correctAnswer = question.GetCorrectAnswer();
        Random rand = new Random();
        if (rand.nextDouble() < 0.9) {
            console.println("Expert: I am almost sure the correct answer is " + correctAnswer);
        } else {
            console.println("Expert: I'm not sure, but I would guess " + (rand.nextInt(4) + 1));
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
        return "1".equals(console.readline("Your choice: "));
    }

    private void handleWrongAnswer(int correctAnswer) {
        console.paragraph();
        console.println("Correct Answer: " + correctAnswer);
        console.println("Wrong! Game over.");
        int winnings = points >= 10 ? 32000 : points >= 5 ? 1000 : 0;
        console.println("You won " + winnings);
    }

    private int generatePrize() {
        prize winnings = new prize(points);
        return winnings.generate();
    }
}
