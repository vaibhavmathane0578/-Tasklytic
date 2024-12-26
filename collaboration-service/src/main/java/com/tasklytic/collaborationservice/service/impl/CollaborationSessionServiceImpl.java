package com.tasklytic.collaborationservice.service.impl;

import com.tasklytic.shared.constants.Constants;
import com.tasklytic.shared.constants.Constants.Exceptions;
import com.tasklytic.collaborationservice.dto.CollaborationSessionDTO;
import com.tasklytic.collaborationservice.model.CollaborationSessionEntity;
import com.tasklytic.collaborationservice.repository.CollaborationSessionRepository;
import com.tasklytic.collaborationservice.service.CollaborationSessionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollaborationSessionServiceImpl implements CollaborationSessionService {

    @Autowired
    private CollaborationSessionRepository collaborationSessionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CollaborationSessionDTO createCollaborationSession(CollaborationSessionDTO collaborationSessionDTO) {
        // Map DTO to Entity
        CollaborationSessionEntity collaborationSession = modelMapper.map(collaborationSessionDTO, CollaborationSessionEntity.class);
        collaborationSession.setCreatedAt(LocalDateTime.now());
        collaborationSession.setLastUpdatedAt(LocalDateTime.now());

        CollaborationSessionEntity savedSession = collaborationSessionRepository.save(collaborationSession);

        // Map saved entity back to DTO
        return modelMapper.map(savedSession, CollaborationSessionDTO.class);
    }

    @Override
    public CollaborationSessionDTO updateCollaborationSession(String id, CollaborationSessionDTO collaborationSessionDTO) {
        CollaborationSessionEntity existingSession = collaborationSessionRepository.findById(id)
                .orElseThrow(() -> new Exceptions.CollaborationSessionNotFoundException(String.format(Constants.SESSION_NOT_FOUND, id)));

        modelMapper.map(collaborationSessionDTO, existingSession);  // Map updated fields
        existingSession.setLastUpdatedAt(LocalDateTime.now());

        CollaborationSessionEntity updatedSession = collaborationSessionRepository.save(existingSession);
        return modelMapper.map(updatedSession, CollaborationSessionDTO.class);
    }

    @Override
    public void deleteCollaborationSession(String id) {
        collaborationSessionRepository.deleteById(id);
    }

    @Override
    public CollaborationSessionDTO getCollaborationSessionById(String id) {
        CollaborationSessionEntity session = collaborationSessionRepository.findById(id)
                .orElseThrow(() -> new Exceptions.CollaborationSessionNotFoundException(String.format(Constants.SESSION_NOT_FOUND, id)));

        return modelMapper.map(session, CollaborationSessionDTO.class);
    }

    @Override
    public List<CollaborationSessionDTO> getCollaborationSessionsByTaskId(String taskId) {
        return collaborationSessionRepository.findByTaskId(taskId).stream()
                .map(session -> modelMapper.map(session, CollaborationSessionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CollaborationSessionDTO> getAllCollaborationSessions() {
        List<CollaborationSessionEntity> sessions = collaborationSessionRepository.findAll();
        return sessions.stream()
                    .map(session -> modelMapper.map(session, CollaborationSessionDTO.class))
                    .collect(Collectors.toList());
    }
}
