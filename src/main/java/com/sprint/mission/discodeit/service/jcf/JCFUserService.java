package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class JCFUserService implements UserService {
    private final Map<UUID, User> data;
    private final Logger log;

    public JCFUserService(Logger log) {
        this.data = new HashMap<>();
        this.log = log;
    }

    @Override
    public User createUser(String nickname, String username, String email, String password, String phoneNumber) {
        User user = new User(nickname, username, email, password, phoneNumber);
        this.data.put(user.getId(), user);

        log.info("{} has been created successfully. ✅ [ID: {}]", nickname, user.getId());
        return user;
    }

    @Override
    public User getUserById(UUID id) {
        User user = this.data.get(id);
        if (user == null) throw new IllegalArgumentException("requested user not found. ❌");

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(this.data.values());
    }

    @Override
    public User updateUser(UUID id, String nickname, String username, String email, String password, String phoneNumber) {
        User user = this.data.get(id);
        if (user == null) throw new IllegalArgumentException("requested user not found. ❌");

        if (nickname != null) user.updateNickname(nickname);
        if (username != null) user.updateUsername(username);
        if (email != null) user.updateEmail(email);
        if (password != null) user.updatePassword(password);
        if (phoneNumber != null) user.updatePhoneNumber(phoneNumber);

        log.info("{} has been updated successfully. ✅ [ID: {}]", user.getNickname(), id);
        return user;
    }

    @Override
    public void deleteUser(UUID id) {
        User user = this.data.remove(id);
        if (user == null) throw new IllegalArgumentException("requested user not found. ❌");

        for (Channel channel : user.getChannels()) {
            channel.removeParticipant(user);
        }

        log.info("{} has been deleted successfully and left from all channels. ✅ [ID: {}]", user.getNickname(), id);
    }
}
