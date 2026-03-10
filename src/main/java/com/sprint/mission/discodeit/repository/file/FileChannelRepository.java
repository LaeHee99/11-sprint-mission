package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileChannelRepository implements ChannelRepository {
    private final String CHANNEL_FILE = "channels.ser";

    private List<Channel> loadChannels() {
        File file = new File(CHANNEL_FILE);

        if(!file.exists()) {
            return new ArrayList<>();
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Channel>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("채널 로드실패", e);
        }
    }

    private void saveChannel(List<Channel> channels) {
        File file = new File(CHANNEL_FILE);

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(channels);
        } catch (IOException e) {
            throw new RuntimeException("채널 저장실패", e);
        }
    }

    @Override
    public void save(Channel channel) {
        List<Channel> channels = loadChannels();
        channels.add(channel);
        saveChannel(channels);
    }

    @Override
    public Optional<Channel> findById(UUID id) {
        return loadChannels().stream()
                .filter(channel -> channel.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Channel> findByPassword(String password) {
        return loadChannels().stream()
                .filter(channel -> channel.getChannelPassword().equals(password))
                .findFirst();
    }

    @Override
    public List<Channel> findAll() {
        return loadChannels();
    }

    @Override
    public void delete(Channel channel) {
        List<Channel> channels = loadChannels();
        channels.removeIf(c -> c.getId().equals(channel.getId()));
        saveChannel(channels);
    }

    public void update(Channel channel) {
        List<Channel> channels = loadChannels();

        for (int i = 0; i < channels.size(); i++) {
            if(channels.get(i).getId().equals(channel.getId())) {
                channels.set(i, channel);
                break;
            }
        }
        saveChannel(channels);
    }
}
