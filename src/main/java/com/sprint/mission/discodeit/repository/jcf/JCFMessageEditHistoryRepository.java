package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.MessageEditHistory;
import com.sprint.mission.discodeit.repository.MessageEditHistoryRepository;

import java.util.*;

public class JCFMessageEditHistoryRepository implements MessageEditHistoryRepository {
    private final Map<UUID, MessageEditHistory> data;

    public JCFMessageEditHistoryRepository() {
        this.data = new HashMap<>();
    }

    @Override
    public void save(MessageEditHistory history) {
        data.put(history.getId(), history);
    }

    @Override
    public MessageEditHistory findById(UUID id) {
        return data.get(id);
    }

    @Override
    public List<MessageEditHistory> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void deleteById(UUID id) {
        data.remove(id);
    }
}
