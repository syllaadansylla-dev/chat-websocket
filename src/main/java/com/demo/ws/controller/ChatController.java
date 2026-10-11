package com.demo.ws.controller;

import com.demo.ws.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private final SimpMessagingTemplate messagingTemplate;
    private final List<ChatMessage> historique = new ArrayList<>();

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessage recevoir(@Payload ChatMessage message) {
        log.info(" {} : {}", message.getExpediteur(), message.getContenu());
        message.setType("CHAT");
        historique.add(message);
        if (historique.size() > 50) historique.remove(0);
        return message;
    }

    @MessageMapping("/chat/join")
    @SendTo("/topic/chat")
    public ChatMessage rejoindre(@Payload ChatMessage message) {
        message.setType("JOIN");
        message.setContenu(message.getExpediteur() + " a rejoint le chat");
        log.info(" {} a rejoint", message.getExpediteur());
        return message;
    }

    @MessageMapping("/chat/leave")
    @SendTo("/topic/chat")
    public ChatMessage quitter(@Payload ChatMessage message) {
        message.setType("LEAVE");
        message.setContenu(message.getExpediteur() + " a quitté le chat");
        log.info(" {} a quitté", message.getExpediteur());
        return message;
    }

    @GetMapping("/api/diffuser")
    @ResponseBody
    public String diffuser() {
        ChatMessage msg = new ChatMessage(
                "Système",
                "Message diffusé via REST à " + java.time.LocalTime.now(),
                "CHAT"
        );
        messagingTemplate.convertAndSend("/topic/chat", msg);
        log.info("Diffusion REST envoyée");
        return "Message diffusé à /topic/chat";
    }

    @GetMapping("/api/historique")
    @ResponseBody
    public List<ChatMessage> historique() {
        return historique;
    }
}