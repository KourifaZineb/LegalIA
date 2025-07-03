package com.chatbot.commonlibrary.dtos.chat;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatResponse {
    private String answer;
    private String title;
}