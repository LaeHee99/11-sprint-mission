package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class JCFUserService implements UserService {
    private final Map<UUID, User> data;
    private static final Logger log = LoggerFactory.getLogger(JCFUserService.class);
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String PHONE_REGEX = "^\\d{3}-\\d{3}-\\d{4}$";

    public JCFUserService() {
        this.data = new HashMap<>();
    }

    @Override
    public User createUser(String nickname, String username, String email, String password, String phoneNumber) {
        if (nickname.isEmpty()) throw new IllegalArgumentException("nickname is required. ❌");

        if (username.isEmpty()) throw new IllegalArgumentException("username is required. ❌");
        if (existUserByUsername(username)) throw new IllegalArgumentException("username cannot be duplicated. ❌");

        if (email.isEmpty()) throw new IllegalArgumentException("email is required. ❌");
        if (!email.matches(EMAIL_REGEX)) throw new IllegalArgumentException("email format is invalid. ❌");
        if (existUserByEmail(email)) throw new IllegalArgumentException("email cannot be duplicated. ❌");

        if (password.isEmpty()) throw new IllegalArgumentException("password is required. ❌");
        if (password.length() < 8) throw new IllegalArgumentException("password length should be at least 8 characters. ❌");

        if (phoneNumber.isEmpty()) throw new IllegalArgumentException("phone number is required. ❌");
        if (!phoneNumber.matches(PHONE_REGEX)) throw new IllegalArgumentException("phone number format is invalid. ❌");

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
    public boolean existUserByUsername(String username) {
        return this.data.values().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    @Override
    public boolean existUserByEmail(String email) {
        return this.data.values().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(this.data.values());
    }

    @Override
    public User updateUser(UUID id, String nickname, String username, String email, String password, String phoneNumber) {
        User user = this.getUserById(id);

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
        User user = this.getUserById(id);

        for (Channel channel : user.getChannels()) {
            channel.removeParticipant(user);
        }

        this.data.remove(id);

        log.info("{} has been deleted successfully and left from all channels. ✅ [ID: {}]", user.getNickname(), id);
    }
}
