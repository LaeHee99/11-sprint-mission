package com.sprint.mission.discodeit.repository.file;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.base.FileRepository;

public class FileMessageRepository extends FileRepository<Message> implements MessageRepository{

    private static FileMessageRepository instance = new FileMessageRepository();
    
    private final Map<UUID, List<Message>> channelIndex = new ConcurrentHashMap<>();
    private final Map<UUID, List<Message>> userIndex = new ConcurrentHashMap<>();

    private FileMessageRepository() {
        super("message.dat");
        rebuildIndex();
    }

    public static FileMessageRepository getInstance() {
        return instance;
    }
    
    private void rebuildIndex() {
        channelIndex.clear();
        userIndex.clear();

        for (Message message : findAll()) {
            addToIndex(message);
        }
    }

    private void addToIndex(Message message) {
        channelIndex.computeIfAbsent(message.getChannelId(), k -> new ArrayList<>()).add(message);
        userIndex.computeIfAbsent(message.getUserId(), k -> new ArrayList<>()).add(message);
    }

    private void removeFromIndex(Message message) {
        List<Message> channelIndexList = channelIndex.get(message.getChannelId());
        if (channelIndexList != null) {
            channelIndexList.removeIf(k -> k.getId().equals(message.getId()));
        }

        List<Message> userIndexList = userIndex.get(message.getUserId());
        if (userIndexList != null) {
            userIndexList.removeIf(k -> k.getId().equals(message.getId()));
        }
    }


    @Override
    public synchronized Message save(Message message) {
        findById(message.getId()).ifPresent(this::removeFromIndex);
        Message savedMessage = super.save(message);
        addToIndex(savedMessage);
        return savedMessage;
    }

    @Override
    public synchronized void deleteById(UUID id) {
        findById(id).ifPresent(this::removeFromIndex);
        super.deleteById(id);
    }

    //
    @Override
    public List<Message> findAllByChannelId(UUID channelId) {
        List<Message> list = channelIndex.getOrDefault(channelId, new ArrayList<>());
        List<Message> sortedList = new ArrayList<>(list);
        sortedList.sort(Comparator.comparing(Message::getCreatedAt));
        return Collections.unmodifiableList(sortedList);
    }

    @Override
    public List<Message> findAllByUserId(UUID userId) {
        List<Message> list = userIndex.getOrDefault(userId, new ArrayList<>());
        List<Message> sortedList = new ArrayList<>(list);
        sortedList.sort(Comparator.comparing(Message::getCreatedAt));
        return Collections.unmodifiableList(new ArrayList<>(list));
    }

}
