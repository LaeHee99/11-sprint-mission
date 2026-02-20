package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public class FileMessageService extends FileUtil implements MessageService {
    public FileMessageService() {
        super("messages");
    }

    @Override
    public void createMessage(Message message) {
        save(filePath(message.getId()), message);
    }

    @Override
    public Message findMessage(UUID id) {
        Path path = filePath(id);
        if(!Files.exists(path)) {
            throw new IllegalArgumentException("Message Not Found");
        }
        return load(path, Message.class);
    }

    @Override
    public List<Message> findAllMessage() {
        return loadAll(directory, Message.class);
    }

    @Override
    public void updateMessage(Message oldMessage, Message newMessage) {
        // UUID를 유지하기 위해 remove -> add 하지 않음
        oldMessage.setContents(newMessage.getContents());
        oldMessage.update();
        save(filePath(oldMessage.getId()), oldMessage);
    }

    @Override
    public void deleteMessage(Message message) {
        delete(filePath(message.getId()));
    }
}
