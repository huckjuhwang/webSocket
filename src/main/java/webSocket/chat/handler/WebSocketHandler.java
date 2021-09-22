package webSocket.chat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import webSocket.chat.domain.ChatMessage;
import webSocket.chat.repository.ChatRoom;
import webSocket.chat.repository.MemoryChatRoomRepository;

import java.util.ArrayList;
import java.util.List;


/**
 * List로 세션을 관리하고 handlerTextMsg 에서 메세지 접속한 세션들 모두에게 뿌려줌으로써
 * 채팅이 이루어짐
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
//    private List<WebSocketSession> sessions = new ArrayList<>();

    private final MemoryChatRoomRepository chatRoomRepository;
    private final ObjectMapper objectMapper;

    /**
     * 연결되는 시점
     */
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        log.info("접속 : {}", session);
//        sessions.add(session);
//    }

    /**
     * 메세지를 전송할때,
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg = message.getPayload();
        log.info("메시지 전송 ={} : {}", session, msg);

        ChatMessage chatMessage = objectMapper.readValue(msg, ChatMessage.class);
        ChatRoom chatRoom = chatRoomRepository.findRoomById(chatMessage.getChatRoomId());
        chatRoom.handleMessage(session, chatMessage, objectMapper);
    }


    /**
     * 연결이 끊어지는 시점.
     */
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        log.info("퇴장 : {}", session);
//        sessions.remove(session);
//    }
}
