package webSocket.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import webSocket.chat.repository.ChatRoom;
import webSocket.chat.repository.MemoryChatRoomRepository;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MemoryChatRoomRepository chatRoomRepository;

    @GetMapping("/")
    public String chat(Model model) {

        List<ChatRoom> rooms = chatRoomRepository.findAllRoom();
        model.addAttribute("rooms", rooms);
        return "rooms";
    }

    @GetMapping("/rooms/{id}")
    public String room(@PathVariable String id, Model model) {
        ChatRoom room = chatRoomRepository.findRoomById(id);
        model.addAttribute("room", room);
        return "room";
    }

    /**
     * chatRoom은  방제목이나 방을 개설한 사람들의 정보를 html로 부터 받을 수 있도록 만듬
     *
     * 추후에 더 많은 기능들 업데이트 예정.
     */
    @GetMapping("/new")
    public String make(Model model) {
        ChatRoomForm form = new ChatRoomForm();
        model.addAttribute("form", form);
        return "newRoom";
    }

    @PostMapping("/room/new")
    public String makeRoom(ChatRoom form) {
        chatRoomRepository.createChatRoom(form.getName());

        return "redirect:/";
    }
}
