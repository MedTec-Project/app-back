package br.medtec.features.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws/notification")
@ApplicationScoped
public class NotificationWebSocket {

    private static final Set<Session> sessions = ConcurrentHashMap.newKeySet();

    private static final ObjectMapper mapper = new ObjectMapper();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        session.getAsyncRemote().sendText("Echo: " + message);
    }

    public static void broadcast(String message) {
        sessions.forEach(session -> {
            if (session.isOpen()) {
                session.getAsyncRemote().sendText(message);
            }
        });
    }

    public static void broadcast(MessageDTO message) {
        try {
            String json = mapper.writeValueAsString(message);
            sessions.forEach(session -> {
                if (session.isOpen()) {
                    session.getAsyncRemote().sendText(json);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendMessages(List<MessageDTO> messages) {
        try {
            String json = mapper.writeValueAsString(messages);
            sessions.forEach(session -> {
                if (session.isOpen()) {
                    session.getAsyncRemote().sendText(json);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
