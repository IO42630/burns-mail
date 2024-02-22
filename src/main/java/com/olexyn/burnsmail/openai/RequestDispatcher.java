package com.olexyn.burnsmail.openai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@UtilityClass
public class RequestDispatcher {


    public static String dispatch(String model, String systemContent, String userContent) throws JsonProcessingException {
        // Create RestTemplate instance
        var restTemplate = new RestTemplate();

        // Set headers
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + System.getenv("openai.api.key"));

        // Create request entity
        String url = "https://api.openai.com/v1/chat/completions";

        var chatRequest = new ChatRequest();
        chatRequest.setModel(model);
        chatRequest.getMessages().add(new ChatMessage("system", systemContent));
        chatRequest.getMessages().add(new ChatMessage("user", userContent));
        var om = new ObjectMapper();

        String jsonBody = om.writeValueAsString(chatRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        // Make the HTTP POST request
        ResponseEntity<String> responseEntity = restTemplate
            .exchange(url, HttpMethod.POST, requestEntity, String.class);

        // Handle response
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode == HttpStatus.OK) {
            return responseEntity.getBody();
            // Process response body
        } else {
            return "error";
            // Handle error
        }
    }
}
