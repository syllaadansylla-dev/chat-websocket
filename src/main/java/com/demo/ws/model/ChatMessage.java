package com.demo.ws.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatMessage {

    private String expediteur;
    private String contenu;
    private String type;
    private String dateEnvoi;

    public ChatMessage() {
        this.dateEnvoi = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public ChatMessage(String expediteur, String contenu, String type) {
        this();
        this.expediteur = expediteur;
        this.contenu = contenu;
        this.type = type;
    }

    public String getExpediteur() { return expediteur; }
    public void setExpediteur(String expediteur) { this.expediteur = expediteur; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDateEnvoi() { return dateEnvoi; }
    public void setDateEnvoi(String dateEnvoi) { this.dateEnvoi = dateEnvoi; }
}