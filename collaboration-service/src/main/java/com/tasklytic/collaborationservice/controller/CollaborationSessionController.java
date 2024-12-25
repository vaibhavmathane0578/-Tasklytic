package com.tasklytic.collaborationservice.controller;

import com.tasklytic.collaborationservice.dto.CollaborationSessionDTO;
import com.tasklytic.collaborationservice.service.CollaborationSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collaboration-sessions")
public class CollaborationSessionController {

    @Autowired
    private CollaborationSessionService service;

    @GetMapping("/{id}")
    public CollaborationSessionDTO getSessionById(@PathVariable String id) {
        return service.getSessionById(id);
    }

    @PostMapping
    public CollaborationSessionDTO createSession(@RequestBody CollaborationSessionDTO dto) {
        return service.createSession(dto);
    }

    @GetMapping
    public List<CollaborationSessionDTO> getAllSessions() {
        return service.getAllSessions();
    }
}
