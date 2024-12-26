package com.tasklytic.collaborationservice.controller;

import com.tasklytic.collaborationservice.dto.ChatMessageDTO;
import com.tasklytic.collaborationservice.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/chat-messages")
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    // Send a new chat message
    @PostMapping
    public ResponseEntity<ChatMessageDTO> sendMessage(@RequestBody @Valid ChatMessageDTO messageDTO) {
        ChatMessageDTO sentMessage = chatMessageService.sendChatMessage(messageDTO);
        return ResponseEntity.ok(sentMessage);
    }

    // Get all chat messages for a specific collaboration session
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<ChatMessageDTO>> getMessagesBySessionId(@PathVariable String sessionId) {
        List<ChatMessageDTO> messages = chatMessageService.getChatMessagesBySessionId(sessionId);
        return ResponseEntity.ok(messages);
    }

    // Mark a message as read
    @PutMapping("/mark-read/{messageId}")
    public ResponseEntity<String> markMessageAsRead(@PathVariable String messageId) {
        chatMessageService.markMessageAsRead(messageId);
        return ResponseEntity.ok("Message marked as read");
    }

    // Mark a message as delivered
    @PutMapping("/mark-delivered/{messageId}")
    public ResponseEntity<String> markMessageAsDelivered(@PathVariable String messageId) {
        chatMessageService.markMessageAsDelivered(messageId);
        return ResponseEntity.ok("Message marked as delivered");
    }
}
