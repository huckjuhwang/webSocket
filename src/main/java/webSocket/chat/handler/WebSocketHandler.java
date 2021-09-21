package webSocket.chat.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * List로 세션을 관리하고 handlerTextMsg 에서 메세지 접속한 세션들 모두에게 뿌려줌으로써
 * 채팅이 이루어짐
 */

@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private List<WebSocketSession> sessions = new ArrayList<>();

    /**
     * 연결되는 시점
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("접속 : {}", session);
        sessions.add(session);
    }

    /**
     * 메세지를 전송할때,
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("메시지 전송 ={} : {}", session, message.getPayload());
        for (WebSocketSession webSocketSession : sessions) {
            TextMessage textMessage = new TextMessage(message.getPayload());
            webSocketSession.sendMessage(textMessage);
        }
    }


    /**
     * 연결이 끊어지는 시점.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("퇴장 : {}", session);
        sessions.remove(session);
    }
}
