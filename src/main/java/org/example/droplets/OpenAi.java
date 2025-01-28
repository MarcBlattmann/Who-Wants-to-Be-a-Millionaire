package org.example.droplets;

import java.net.http.*;
import java.net.URI;
import org.json.JSONObject;

public class OpenAi {
    private final String API_KEY;

    public OpenAi(String API_KEY) {
        this.API_KEY = API_KEY;
    }


    public String ask(String userInput) {
        var requestBody = """
            {
                "model": "gpt-3.5-turbo",
                "messages": [
                    {"role": "system", "content": "Provide a random question not every time the same followed by four answer options: question, first answer, second answer, third answer, fourth answer, and then the number of the correct answer. No text formatting, just plain text, no additional text or letters or numbers, just | in between. Not always capitals of city's please and dont give out the same question multiple times"},
                    {"role": "user", "content": "%s"}
                ]
            }
            """.formatted(userInput);

        var client = HttpClient.newHttpClient();

        var API_URL = "https://api.openai.com/v1/chat/completions";
        var request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // Parse the response JSON to extract the message
            JSONObject jsonResponse = new JSONObject(response.body());
            return jsonResponse.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
        } catch (Exception e) {
            return "An error occurred: " + e.getMessage();
        }
    }

}
