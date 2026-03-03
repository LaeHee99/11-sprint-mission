package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Domain.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.io.*;
import java.util.*;

public class FileMessageRepository implements MessageRepository {
    private Map<UUID, Message> data;

    // TODO
    // 저장(saveToFile), 불러오기 (loadFromFile) 구현
    private void saveToFile(){
        try (FileOutputStream fos = new FileOutputStream("Message.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadFromFile(){
        File file = new File("Message.ser");
        if (!file.exists()) {
            return;
        }
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            this.data = (Map<UUID, Message>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public FileMessageRepository() {
        this.data = new HashMap<>();
        loadFromFile();
    }

    @Override
    public UUID create(Message message) {
        data.put(message.getId(), message);
        saveToFile();
        return message.getId();
    }

    @Override
    public Message read(UUID id) {
        return data.get(id);
    }   // key인 id로 메세지 내용 읽기
    // 여기서 메세지는 보낸사람, 받는 사람 포함임

    @Override
    public List<Message> readAll(){
        return new ArrayList<>(data.values());
    }   // value값들 list


    @Override
    public void delete(UUID id) {
        data.remove(id);
        saveToFile();
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
