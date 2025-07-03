package com.chatbotservice.services;

import com.chatbot.commonlibrary.dtos.chat.ChatResponse;

import java.util.List;

public interface ModelInferenceService {

    ChatResponse infer(String sessionId, String question);
}
