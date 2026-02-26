package com.sprint.mission.discodeit.entity.Domain;

import java.io.Serializable;
import java.util.UUID;

public class Message extends BaseEntity implements Serializable {
    private String messageContent;     // 내용
    private String messageSender;        // 보낸이
    private String messageReceiver;     // 받는이
    private static final long serialVersionUID = 1L;

    public Message(String messageContent, String messageSender, String messageReceiver) {
        super();
        this.messageContent = messageContent;
        this.messageSender = messageSender;
        this.messageReceiver = messageReceiver;
    }

    public String getContent() {return messageContent;}
    public String getSender() {return messageSender;}
    public String getReceiver() {return messageReceiver;}

    public void updateContent( String messageContent) {
        this.messageContent = messageContent;
        updateTimestamp();
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender='" + messageSender + '\'' +
                ", receiver='" + messageReceiver + '\'' +
                ", content='" + messageContent + '\'' +
                '}';
    }
}
