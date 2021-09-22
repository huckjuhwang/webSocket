package webSocket.chat.repository;

import javax.annotation.PostConstruct;
import java.util.List;

interface ChatRoomRepository {

    public void init();

    /**
     * 방 전체 목록 출력
     */
    public List<ChatRoom> findAllRoom();


    /**
     * 메시지를 받아 룸번호 전송
     */
    public ChatRoom findRoomById(String id);


    /**
     * 새로운 방을 생성
     */
    public ChatRoom createChatRoom(String name);

}
