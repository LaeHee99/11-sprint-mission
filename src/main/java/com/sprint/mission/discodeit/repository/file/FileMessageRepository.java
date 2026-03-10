package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileMessageRepository implements MessageRepository {
    private final String MESSAGE_FILE = "messages.ser";

    private List<Message> loadMessages() {
        File file = new File(MESSAGE_FILE);

        if(!file.exists()) {
            return new ArrayList<>();
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Message>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("매세지 로드실패", e);
        }
    }

    private void saveMessage(List<Message> messages) {
        File file = new File(MESSAGE_FILE);

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(messages);
        } catch (IOException e) {
            throw new RuntimeException("메세지 저장실패", e);
        }
    }

    @Override
    public void save(Message message) {
        List<Message> messages = loadMessages();
        messages.add(message);
        saveMessage(messages);
    }

    @Override
    public Optional<Message> findById(UUID id) {
        return loadMessages().stream()
                .filter(message -> message.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Message> findAll() {
        return loadMessages();
    }

    @Override
    public void delete(Message message) {
        List<Message> messages = loadMessages();
        messages.removeIf(m -> m.getId().equals(message.getId()));
        saveMessage(messages);
    }

    @Override
    public void update(Message message) {
        List<Message> messages = loadMessages();

        for (int i = 0; i < messages.size(); i++) {
            if(messages.get(i).getId().equals(message.getId())) {
                messages.set(i, message);
                break;
            }
        }

        saveMessage(messages);
    }
}
