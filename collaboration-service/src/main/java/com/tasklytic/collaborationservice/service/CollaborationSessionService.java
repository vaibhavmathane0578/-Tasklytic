package com.tasklytic.collaborationservice.service;

import com.tasklytic.collaborationservice.dto.CollaborationSessionDTO;

import java.util.List;

public interface CollaborationSessionService {

    // Create a new collaboration session
    CollaborationSessionDTO createCollaborationSession(CollaborationSessionDTO collaborationSessionDTO);

    // Update an existing collaboration session
    CollaborationSessionDTO updateCollaborationSession(String id, CollaborationSessionDTO collaborationSessionDTO);

    // Delete a collaboration session by its ID
    void deleteCollaborationSession(String id);

    // Get collaboration session by its ID
    CollaborationSessionDTO getCollaborationSessionById(String id);

    // Get all collaboration sessions for a particular task
    List<CollaborationSessionDTO> getCollaborationSessionsByTaskId(String taskId);

    // Get all collaboration sessions
    List<CollaborationSessionDTO> getAllCollaborationSessions();
}
