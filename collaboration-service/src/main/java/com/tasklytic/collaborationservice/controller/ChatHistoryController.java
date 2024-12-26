package com.tasklytic.collaborationservice.controller;

import com.tasklytic.collaborationservice.dto.ChatMessageDTO;
import com.tasklytic.collaborationservice.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat-history")
public class ChatHistoryController {

    @Autowired
    private ChatMessageService chatMessageService;

    // Get all chat messages for a specific collaboration session
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<ChatMessageDTO>> getChatHistoryBySessionId(@PathVariable String sessionId) {
        List<ChatMessageDTO> messages = chatMessageService.getChatMessagesBySessionId(sessionId);
        return ResponseEntity.ok(messages);
    }

    // Get chat messages for a specific collaboration session and sender
    @GetMapping("/session/{sessionId}/sender/{senderId}")
    public ResponseEntity<List<ChatMessageDTO>> getChatHistoryBySessionIdAndSenderId(
            @PathVariable String sessionId,
            @PathVariable String senderId
    ) {
        List<ChatMessageDTO> messages = chatMessageService.getChatMessagesBySenderIdAndSessionId(sessionId, senderId);
        return ResponseEntity.ok(messages);
    }

    // Delete all chat messages for a specific collaboration session
    @DeleteMapping("/session/{sessionId}")
    public ResponseEntity<String> deleteChatHistoryBySessionId(@PathVariable String sessionId) {
        chatMessageService.deleteChatMessagesBySessionId(sessionId);
        return ResponseEntity.ok("Chat history for session deleted successfully.");
    }
}
