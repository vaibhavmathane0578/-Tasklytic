package com.tasklytic.collaborationservice.service;

import com.tasklytic.collaborationservice.dto.ChatMessageDTO;

import java.util.List;

public interface ChatMessageService {

    // Create a new chat message
    ChatMessageDTO sendChatMessage(ChatMessageDTO chatMessageDTO);

    // Get all chat messages for a specific collaboration session
    List<ChatMessageDTO> getChatMessagesBySessionId(String collaborationSessionId);

    // Get chat messages by sender ID and collaboration session ID
    List<ChatMessageDTO> getChatMessagesBySenderIdAndSessionId(String collaborationSessionId, String senderId);

    // Delete all chat messages for a specific collaboration session
    void deleteChatMessagesBySessionId(String collaborationSessionId);

    // Mark a message as read
    void markMessageAsRead(String messageId);

    // Mark a message as delivered
    void markMessageAsDelivered(String messageId);
}
