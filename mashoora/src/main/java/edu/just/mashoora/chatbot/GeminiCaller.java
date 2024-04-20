package edu.just.mashoora.chatbot;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiCaller {

    private static String apiKey = "YOUR-GEMINI-API-KEY";

    public static String generateContent(String query) {

        // URL of the Google API
        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + apiKey;

        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Request body
        String requestBody = "{\"contents\":[{\"parts\":[{\"text\":\"" + query + "\"}]}]}";

        // Create HttpEntity with headers and body
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // Make the HTTP POST request
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response.getBody());
            JSONArray candidates = jsonResponse.getJSONArray("candidates");
            JSONObject content = candidates.getJSONObject(0).getJSONObject("content");
            JSONArray parts = content.getJSONArray("parts");

            // Extract and concatenate text parts
            StringBuilder textResponse = new StringBuilder();
            for (int i = 0; i < parts.length(); i++) {
                JSONObject part = parts.getJSONObject(i);
                String text = part.getString("text");
                textResponse.append(text);
            }

            return textResponse.toString();
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
