package com.tasklytic.collaborationservice.controller;

import com.tasklytic.collaborationservice.model.ChatMessageEntity;
import com.tasklytic.collaborationservice.repository.ChatMessageRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chats")
public class ChatHistoryController {

    private final ChatMessageRepository chatMessageRepository;

    public ChatHistoryController(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @GetMapping("/{sessionId}")
    public List<ChatMessageEntity> getChatHistory(@PathVariable Long sessionId) {
        return chatMessageRepository.findBySessionId(sessionId);
    }
}
