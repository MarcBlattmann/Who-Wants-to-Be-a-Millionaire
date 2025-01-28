package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.droplets.OpenAi;

public class Question {

    Dotenv dotenv = Dotenv.load();
    OpenAi ChatGPT = new OpenAi(dotenv.get("API_KEY"));

    String Question;
    String[] Answers;
    int CorrectAnswer;

    public Question(int difficulty) {
        var output = ChatGPT.ask(("Give me a question with the difficulty" + difficulty + "/15"));
        String[] split = output.split("\\|");
        Question = split[0];
        Answers = new String[]{split[1].replaceAll("\\s+",""), split[2].replaceAll("\\s+",""), split[3].replaceAll("\\s+",""), split[4].replaceAll("\\s+","")};
        CorrectAnswer = Integer.parseInt(split[5].replaceAll("\\s+",""));
    }

    /**
     * It gives you the question text
     */
    public String GetQuestion() {
        return Question;
    }

    /**
     * It gives you the 4 answers in an array
     */
    public String[] GetAnswers() {
        return Answers;
    }

    /**
     * It gives you the number of the correct answer in a number form 1 - 4
     */
    public int GetCorrectAnswer() {
        return CorrectAnswer;
    }
}
