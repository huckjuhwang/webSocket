package webSocket.chat.repository;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;


/**
 * 메모리에 먼저 저장하고 개발 이후 JPA로 수정 예정.
 */
@Repository
public class MemoryChatRoomRepository implements ChatRoomRepository {

    private Map<String, ChatRoom> chatRoomMap;

    @Override
    @PostConstruct
    public void init() {
        chatRoomMap = new LinkedHashMap<>();
    }
    /**
     * 방 전체 목록 출력
     */
    @Override
    public List<ChatRoom> findAllRoom() {
        List chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }
    /**
     * 메시지를 받아 룸번호 전송
     */
    @Override
    public ChatRoom findRoomById(String id) {
        return chatRoomMap.get(id);
    }
    /**
     * 새로운 방을 생성
     */
    @Override
    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
}
