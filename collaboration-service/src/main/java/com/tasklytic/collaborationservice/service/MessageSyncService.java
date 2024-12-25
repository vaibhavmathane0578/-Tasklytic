package com.tasklytic.collaborationservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tasklytic.collaborationservice.model.ChatMessageEntity;
import com.tasklytic.collaborationservice.repository.ChatMessageRepository;

@Service
public class MessageSyncService {

    private final ChatMessageRepository chatMessageRepository;

    public MessageSyncService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public List<ChatMessageEntity> getUndeliveredMessages(Long sessionId) {
        return chatMessageRepository.findBySessionId(sessionId)
                .stream()
                .filter(message -> !message.isDelivered())
                .toList();
    }

    public void markMessagesAsDelivered(List<ChatMessageEntity> messages) {
        messages.forEach(message -> message.setDelivered(true));
        chatMessageRepository.saveAll(messages);
    }
}
