package com.chatbotservice.services;

import java.util.List;

public interface ModelInferenceService {

    String infer(String sessionId, String question);
}
