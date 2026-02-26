package com.sprint.mission.discodeit.service.file;
import com.sprint.mission.discodeit.entity.Domain.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.io.*;
import java.util.*;

public class FileChannelService implements ChannelService {
    private Map<UUID, Channel> data ;

    // TODO
    // 저장(saveToFile), 불러오기 (loadFromFile) 구현
    private void saveToFile(){
        File file = new File("Channel.ser");
        if (!file.exists()) return;

        try (FileOutputStream fos = new FileOutputStream("Channel.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadFromFile(){
        File file = new File("Channel.ser");
        if (!file.exists()) return;  // 파일 없으면 그냥 넘어가기

        try (FileInputStream fis = new FileInputStream("Channel.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            this.data = (Map<UUID, Channel>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public FileChannelService() {
        this.data = new HashMap<>();
        loadFromFile();
    }

    @Override
    public UUID create(Channel channel) {
        data.put(channel.getId(), channel);
        saveToFile();
        return channel.getId();
    }

    @Override
    public Channel read(UUID id) { return data.get(id); }

    @Override
    public List<Channel> readAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void update(UUID id, String newName, String newDescription) {
        Channel channel = data.get(id);
        channel.updateChannel(newName, newDescription);
        saveToFile();
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
        saveToFile();
    }
}
