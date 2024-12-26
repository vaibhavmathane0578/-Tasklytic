package com.tasklytic.collaborationservice.controller;

import com.tasklytic.collaborationservice.dto.CollaborationSessionDTO;
import com.tasklytic.collaborationservice.service.CollaborationSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/collaboration-sessions")
public class CollaborationSessionController {

    @Autowired
    private CollaborationSessionService collaborationSessionService;

    // Create a new collaboration session
    @PostMapping
    public ResponseEntity<CollaborationSessionDTO> createSession(@RequestBody @Valid CollaborationSessionDTO sessionDTO) {
        CollaborationSessionDTO createdSession = collaborationSessionService.createCollaborationSession(sessionDTO);
        return ResponseEntity.ok(createdSession);
    }

    // Get a collaboration session by ID
    @GetMapping("/{sessionId}")
    public ResponseEntity<CollaborationSessionDTO> getSessionById(@PathVariable String sessionId) {
        CollaborationSessionDTO session = collaborationSessionService.getCollaborationSessionById(sessionId);
        return ResponseEntity.ok(session);
    }

    // Get all collaboration sessions for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CollaborationSessionDTO>> getSessionsByTaskId(@PathVariable String taskId) {
        List<CollaborationSessionDTO> sessions = collaborationSessionService.getCollaborationSessionsByTaskId(taskId);
        return ResponseEntity.ok(sessions);
    }

    // Delete a collaboration session by ID
    @DeleteMapping("/{sessionId}")
    public ResponseEntity<String> deleteSession(@PathVariable String sessionId) {
        collaborationSessionService.deleteCollaborationSession(sessionId);
        return ResponseEntity.ok("Collaboration session deleted successfully.");
    }
}
