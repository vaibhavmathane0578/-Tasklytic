package com.tasklytic.collaborationservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasklytic.collaborationservice.dto.ChatMessageDTO;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, CopyOnWriteArraySet<WebSocketSession>> sessionMap = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON handling

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // Extract collaborationSessionId and senderId from WebSocket URI query parameters
        String collaborationSessionId = getQueryParameter(session, "collaborationSessionId");
        String senderId = getQueryParameter(session, "senderId");

        if (collaborationSessionId == null || collaborationSessionId.isEmpty() || senderId == null || senderId.isEmpty()) {
            try {
                session.close(CloseStatus.BAD_DATA); // Close connection if required parameters are missing
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.err.println("Connection rejected: Missing collaborationSessionId or senderId");
            return;
        }

        session.getAttributes().put("senderId", senderId); // Store senderId in session attributes
        sessionMap.computeIfAbsent(collaborationSessionId, key -> new CopyOnWriteArraySet<>()).add(session);
        System.out.println("User joined session: " + collaborationSessionId + " with senderId: " + senderId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String collaborationSessionId = getQueryParameter(session, "collaborationSessionId");

        if (collaborationSessionId == null || collaborationSessionId.isEmpty()) {
            session.close(CloseStatus.BAD_DATA); // Close connection if collaborationSessionId is missing
            return;
        }

        // Deserialize JSON payload to ChatMessageDTO
        ChatMessageDTO chatMessageDTO = objectMapper.readValue(message.getPayload(), ChatMessageDTO.class);

        // Handle message types
        switch (chatMessageDTO.getMessage()) {
            case "TYPING":
                broadcastTypingStatus(collaborationSessionId, session, chatMessageDTO);
                break;

            case "READ":
                broadcastReadStatus(collaborationSessionId, chatMessageDTO);
                break;

            default:
                broadcastMessage(collaborationSessionId, chatMessageDTO);
                broadcastDeliveredStatus(collaborationSessionId, chatMessageDTO); // Mark as delivered
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String collaborationSessionId = getQueryParameter(session, "collaborationSessionId");

        if (collaborationSessionId != null) {
            sessionMap.getOrDefault(collaborationSessionId, new CopyOnWriteArraySet<>()).remove(session);
            System.out.println("User left session: " + collaborationSessionId);
        }
    }

    private void broadcastTypingStatus(String sessionId, WebSocketSession sender, ChatMessageDTO messageDTO) {
        messageDTO.setMessage("TYPING");
        messageDTO.setSenderId(getSenderIdFromSession(sender));
        broadcastMessage(sessionId, messageDTO);
    }

    private void broadcastReadStatus(String sessionId, ChatMessageDTO messageDTO) {
        messageDTO.setMessage("READ");
        broadcastMessage(sessionId, messageDTO);
    }

    private void broadcastDeliveredStatus(String sessionId, ChatMessageDTO messageDTO) {
        messageDTO.setMessage("DELIVERED");
        broadcastMessage(sessionId, messageDTO);
    }

    private void broadcastMessage(String sessionId, ChatMessageDTO messageDTO) {
        CopyOnWriteArraySet<WebSocketSession> sessions = sessionMap.get(sessionId);
        if (sessions != null) {
            for (WebSocketSession wsSession : sessions) {
                if (wsSession.isOpen()) {
                    try {
                        String jsonResponse = objectMapper.writeValueAsString(messageDTO);
                        wsSession.sendMessage(new TextMessage(jsonResponse));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String getQueryParameter(WebSocketSession session, String paramName) {
        String query = session.getUri().getQuery();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] parts = param.split("=");
                if (parts.length == 2 && parts[0].equals(paramName)) {
                    return parts[1];
                }
            }
        }
        return null;
    }

    private String getSenderIdFromSession(WebSocketSession session) {
        // Retrieve senderId from session attributes (it was saved in afterConnectionEstablished method)
        return (String) session.getAttributes().get("senderId");
    }
}
