package webSocket.chat.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import webSocket.chat.domain.ChatMessage;
import webSocket.chat.domain.MessageType;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Slf4j
public class ChatRoom {

    private String roomId; // 방 번호
    private String name; // 방 이름
    private Set<WebSocketSession> sessions;  // 채팅 방에 있는 세션 관리

    public ChatRoom(){
        sessions = new HashSet<>();
    }

    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }

    public void handleMessage(WebSocketSession session, ChatMessage chatMessage, ObjectMapper objectMapper) throws IOException {

        if (chatMessage.getMessageType() == MessageType.ENTER) {
            sessions.add(session);
            log.info("# MessageType.ENTER : {}님이 입장 하셨습니다.", chatMessage.getWriter());
            chatMessage.setMessage(chatMessage.getWriter() + "님이 입장 하셨습니다.");
        }

        else if (chatMessage.getMessageType() == MessageType.LEAVE) {
            sessions.remove(session);
            log.info("# MessageType.LEAVE : {}님이 퇴장 하셨습니다.", chatMessage.getWriter());
            chatMessage.setMessage(chatMessage.getWriter() + "님이 퇴장 하셨습니다.");
        }

        else{
            chatMessage.setMessage(chatMessage.getWriter() + " => " + chatMessage.getMessage());
        }
        send(chatMessage, objectMapper);

    }


    /**
     * 각 방에 맞춰서 메시지를 전송 해준다.
     * @param chatMessage
     * @param objectMapper
     * @throws IOException
     */
    private void send(ChatMessage chatMessage, ObjectMapper objectMapper) throws IOException {
        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(chatMessage.getMessage()));

        for (WebSocketSession session : sessions) {
            session.sendMessage(textMessage);
        }
    }
}