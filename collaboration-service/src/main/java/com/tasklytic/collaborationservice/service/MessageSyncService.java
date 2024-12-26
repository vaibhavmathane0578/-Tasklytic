package com.tasklytic.collaborationservice.service;

import com.tasklytic.collaborationservice.dto.ChatMessageDTO;
import com.tasklytic.collaborationservice.dto.CollaborationSessionDTO;

import java.util.List;

public interface MessageSyncService {

    // Synchronize chat messages across systems/services
    void syncChatMessages(List<ChatMessageDTO> chatMessageDTOList);

    // Synchronize collaboration session events across systems/services
    void syncCollaborationSession(CollaborationSessionDTO collaborationSessionDTO);
}
