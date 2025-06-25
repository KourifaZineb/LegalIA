package com.chatbotservice.services;

import java.util.List;

public interface ChromaRetrievalService {
    List<String> getContext(String query);
}
