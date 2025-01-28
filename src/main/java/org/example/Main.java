package org.example;

//Used for project: https://github.com/cdimascio/dotenv-java , https://github.com/stleary/JSON-java

import org.example.droplets.console;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        var balance = 0;
        while (true) {
            console.paragraph();
            console.println("Balance : " + balance);
            console.readline("Do you want to play (Click enter to play)");
            var game = new Game();
            balance += game.play();
        }
    }
}