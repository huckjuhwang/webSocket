package webSocket.chat.domain;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class ChatMessage {

    private String chatRoomId;
    private String writer;
    private String message;
    private MessageType messageType;
}
