package com.olexyn.burnsmail.openai;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatRequest {

    private String model;
    private List<ChatMessage> messages = new ArrayList<>();

}

