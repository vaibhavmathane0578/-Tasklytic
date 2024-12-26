package com.tasklytic.collaborationservice.service.impl;

import com.tasklytic.collaborationservice.dto.ChatMessageDTO;
import com.tasklytic.collaborationservice.model.ChatMessageEntity;
import com.tasklytic.collaborationservice.repository.ChatMessageRepository;
import com.tasklytic.collaborationservice.service.ChatMessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ChatMessageDTO sendChatMessage(ChatMessageDTO chatMessageDTO) {
        // Map DTO to Entity
        ChatMessageEntity chatMessage = modelMapper.map(chatMessageDTO, ChatMessageEntity.class);
        chatMessage.setSentTimestamp(LocalDateTime.now());

        ChatMessageEntity savedMessage = chatMessageRepository.save(chatMessage);

        // Map saved entity back to DTO
        return modelMapper.map(savedMessage, ChatMessageDTO.class);
    }

    @Override
    public List<ChatMessageDTO> getChatMessagesBySessionId(String collaborationSessionId) {
        List<ChatMessageEntity> messages = chatMessageRepository.findByCollaborationSessionId(collaborationSessionId);
        return messages.stream()
                .map(message -> modelMapper.map(message, ChatMessageDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatMessageDTO> getChatMessagesBySenderIdAndSessionId(String collaborationSessionId, String senderId) {
        List<ChatMessageEntity> messages = chatMessageRepository.findByCollaborationSessionIdAndSenderId(collaborationSessionId, senderId);
        return messages.stream()
                .map(message -> modelMapper.map(message, ChatMessageDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteChatMessagesBySessionId(String collaborationSessionId) {
        List<ChatMessageEntity> messages = chatMessageRepository.findByCollaborationSessionId(collaborationSessionId);
        chatMessageRepository.deleteAll(messages);
    }

    @Override
    public void markMessageAsRead(String messageId) {
        ChatMessageEntity message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message with ID " + messageId + " not found"));
        message.setRead(true);
        message.setReadTimestamp(LocalDateTime.now());
        chatMessageRepository.save(message);
    }

    @Override
    public void markMessageAsDelivered(String messageId) {
        ChatMessageEntity message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message with ID " + messageId + " not found"));
        message.setDelivered(true);
        message.setSentTimestamp(LocalDateTime.now());
        chatMessageRepository.save(message);
    }
}
