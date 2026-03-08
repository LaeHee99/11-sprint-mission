package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.service.file.FileChannelService;
import com.sprint.mission.discodeit.service.file.FileMessageService;
import com.sprint.mission.discodeit.service.file.FileUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JavaApplication {
    private final Logger log = LoggerFactory.getLogger(JavaApplication.class);

    // jcf
//    private final JCFUserService userService;
//    private final JCFChannelService channelService;
//    private final JCFMessageService messageService;
//
//    public JavaApplication() {
//        JCFUserRepository userRepository = new JCFUserRepository();
//        JCFChannelRepository channelRepository = new JCFChannelRepository();
//        JCFMessageRepository messageRepository = new JCFMessageRepository();
//        this.userService = new JCFUserService(userRepository, channelRepository);
//        this.channelService = new JCFChannelService(channelRepository, userRepository, messageRepository);
//        this.messageService = new JCFMessageService(messageRepository, userRepository, channelRepository);
//    }

    // file
    private final FileUserService userService;
    private final FileChannelService channelService;
    private final FileMessageService messageService;

    public JavaApplication() {
        FileUserRepository userRepository = new FileUserRepository();
        FileChannelRepository channelRepository = new FileChannelRepository();
        FileMessageRepository messageRepository = new FileMessageRepository();
        this.userService = new FileUserService(userRepository, channelRepository);
        this.channelService = new FileChannelService(channelRepository, userRepository, messageRepository);
        this.messageService = new FileMessageService(messageRepository, userRepository,channelRepository);
    }

    public static void main(String[] args) {
        JavaApplication app = new JavaApplication();
        app.run();
    }

    public void run() {
        log.info("--------------------------------------------------");
        log.info("Discodeit Service Test Started. 🚀");
        log.info("--------------------------------------------------");

        // User Service Test
        log.info("1. User Service Functionality Test:");

        // create users
        log.info(">> 1-1. Creating Users...");
        User user1 = this.userService.createUser("JohnDoe", "johndoe", "johndoe@codeit.com", "password123", "123-456-7890");
        User user2 = this.userService.createUser("JaneDoe", "janedoe", "janedoe@codeit.com", "password456", "098-765-4321");
        User user3 = this.userService.createUser("AliceSmith", "alicesmith", "alicesmith@codeit.com", "password789", "555-555-5555");
        log.info("--------------------------------------------------");

        // find by id test
        log.info(">> 1-2. Finding Users by ID...");
        User foundUser1 = this.userService.getUserById(user1.getId());
        log.info("Target User ID: {}, Found User Id: {}, matched? {}", user1.getId(), foundUser1.getId(), user1.getId().equals(foundUser1.getId()));
        User foundUser2 = this.userService.getUserById(user2.getId());
        log.info("Target User ID: {}, Found User Id: {}, matched? {}", user2.getId(), foundUser2.getId(), user2.getId().equals(foundUser2.getId()));
        User foundUser3 = this.userService.getUserById(user3.getId());
        log.info("Target User ID: {}, Found User Id: {}, matched? {}", user3.getId(), foundUser3.getId(), user3.getId().equals(foundUser3.getId()));
        log.info("--------------------------------------------------");

        // find all users test
        log.info(">> 1-3. Finding All Users...");
        List<User> users = this.userService.getAllUsers();
        log.info("Total Users Created: 3, Total Users Found: {}, matched? {}", users.size(), users.size() == 3);
        for (User user : users) log.info(user.toString());
        log.info("--------------------------------------------------");

        // update user test
        log.info(">> 1-4. Updating User...");
        User updatedUser1 = this.userService.updateUser(user1.getId(), "JohnUpdated", "johnupdated", "johnupdated@codeit.com", "newpassword123", "111-222-3333");
        log.info("Target User ID: {}, Updated User Id: {}, matched? {}", user1.getId(), updatedUser1.getId(), user1.getId().equals(updatedUser1.getId()));
        log.info(updatedUser1.toString());
        log.info("--------------------------------------------------");

        // delete user test
        log.info(">> 1-5. Deleting User...");
        log.info("Target User ID: {}", user2.getId());
        this.userService.deleteUser(user2.getId());
        log.info("Trying to find deleted user...");
        try {
            this.userService.getUserById(user2.getId());
            log.info("Deleted user still existed. ❌");
        } catch (IllegalArgumentException e) {
            log.info("Expected exception caught. ✅ -> {}", e.getMessage());
        }
        log.info("--------------------------------------------------");

        // Channel Service Test
        log.info("2. Channel Service Functionality Test:");

        // create channels
        log.info(">> 2-1. Creating Channels...");
        Channel channel1 = this.channelService.createChannel("JavaStudy");
        Channel channel2 = this.channelService.createChannel("PhythonStudy");
        Channel channel3 = this.channelService.createChannel("JavaScriptStudy");
        log.info("--------------------------------------------------");

        // find channels by id test
        log.info(">> 2-2. Finding Channels by ID...");
        Channel foundChannel1 = this.channelService.getChannelById(channel1.getId());
        log.info("Target Channel ID: {}, Found Channel Id: {}, matched? {}", channel1.getId(), foundChannel1.getId(), channel1.getId().equals(foundChannel1.getId()));
        Channel foundChannel2 = this.channelService.getChannelById(channel2.getId());
        log.info("Target Channel ID: {}, Found Channel Id: {}, matched? {}", channel2.getId(), foundChannel2.getId(), channel2.getId().equals(foundChannel2.getId()));
        Channel foundChannel3 = this.channelService.getChannelById(channel3.getId());
        log.info("Target Channel ID: {}, Found Channel Id: {}, matched? {}", channel3.getId(), foundChannel3.getId(), channel3.getId().equals(foundChannel3.getId()));
        log.info("--------------------------------------------------");

        // find all channels test
        log.info(">> 2-3. Finding All Channels...");
        List<Channel> channels = this.channelService.getAllChannels();
        log.info("Total Channels Created: 3, Total Channels Found: {}, matched? {}", channels.size(), channels.size() == 3);
        for (Channel channel : channels) log.info(channel.toString());
        log.info("--------------------------------------------------");

        // update channel test
        log.info(">> 2-4. Updating Channel...");
        Channel updatedChannel1 = this.channelService.updateChannel(channel1.getId(), "JavaStudyUpdated");
        log.info("Target Channel ID: {}, Updated Channel Id: {}, matched? {}", channel1.getId(), updatedChannel1.getId(), channel1.getId().equals(updatedChannel1.getId()));
        log.info(updatedChannel1.toString());
        log.info("--------------------------------------------------");

        /// delete channel test
        log.info(">> 2-5. Deleting Channel...");
        log.info("Target Channel ID: {}", channel2.getId());
        this.channelService.deleteChannel(channel2.getId());
        log.info("Trying to find deleted channel...");
        try {
            this.channelService.getChannelById(channel2.getId());
            log.info("Deleted channel still existed. ❌");
        } catch (IllegalArgumentException e) {
            log.info("Expected exception caught. ✅ -> {}", e.getMessage());
        }
        log.info("--------------------------------------------------");

        // Message Service Test
        log.info("3. Message Service Functionality Test:");

        // create messages
        log.info(">> 3-1. Creating Messages...");
        this.channelService.joinChannel(channel1.getId(), user1.getId());
        this.channelService.joinChannel(channel1.getId(), user3.getId());
        Message message1 = this.messageService.createMessage("Hello, this is John!", user1.getId(), channel1.getId());
        Message message2 = this.messageService.createMessage("Hi John, this is Alice!", user3.getId(), channel1.getId());
        Message message3 = this.messageService.createMessage("Great, let’s start at 8 PM. I will upload the material soon.", user1.getId(), channel1.getId());
        log.info("--------------------------------------------------");

        // find messages by id test
        log.info(">> 3-2. Finding Messages by ID...");
        Message foundMessage1 = this.messageService.getMessageById(message1.getId());
        log.info("Target Message ID: {}, Found Message Id: {}, matched? {}", message1.getId(), foundMessage1.getId(), message1.getId().equals(foundMessage1.getId()));
        Message foundMessage2 = this.messageService.getMessageById(message2.getId());
        log.info("Target Message ID: {}, Found Message Id: {}, matched? {}", message2.getId(), foundMessage2.getId(), message2.getId().equals(foundMessage2.getId()));
        Message foundMessage3 = this.messageService.getMessageById(message3.getId());
        log.info("Target Message ID: {}, Found Message Id: {}, matched? {}", message3.getId(), foundMessage3.getId(), message3.getId().equals(foundMessage3.getId()));
        log.info("--------------------------------------------------");

        // find all messages test
        log.info(">> 3-3. Finding All Messages...");
        List<Message> messages = this.messageService.getAllMessages();
        log.info("Total Messages Created: 3, Total Messages Found: {}, matched? {}", messages.size(), messages.size() == 3);
        for (Message message : messages) log.info(message.toString());
        log.info("--------------------------------------------------");

        // update message test
        log.info(">> 3-4. Updating Message...");
        Message updatedMessage1 = this.messageService.updateMessage(message1.getId(), "Hello, this is John! I've updated my message.");
        log.info("Target Message ID: {}, Updated Message Id: {}, matched? {}", message1.getId(), updatedMessage1.getId(), message1.getId().equals(updatedMessage1.getId()));
        log.info(updatedMessage1.toString());
        log.info("--------------------------------------------------");

        // delete message test
        log.info(">> 3-5. Deleting Message...");
        log.info("Target Message ID: {}", message2.getId());
        this.messageService.deleteMessage(message2.getId());
        log.info("Trying to find deleted message...");
        try {
            this.messageService.getMessageById(message2.getId());
            log.info("Deleted message still existed. ❌");
        } catch (IllegalArgumentException e) {
            log.info("Expected exception caught. ✅ -> {}", e.getMessage());
        }
        log.info("--------------------------------------------------");

        // Domain Relation Test
        log.info("4. Domain Relation Test:");

        // leave channel test
        log.info(">> 4-1. Leaving Channel...");
        log.info("Target Channel ID: {}, Leaving User ID: {}", channel1.getId(), user1.getId());
        this.channelService.leaveChannel(channel1.getId(), user1.getId());
        log.info("left? channel: {}, user: {}", !channel1.getParticipants().contains(user1), !user1.getChannels().contains(channel1));
        log.info("--------------------------------------------------");

        // join channel test
        log.info(">> 4-2. Joining Channel...");
        log.info("Target Channel ID: {}, Joining User ID: {}", channel1.getId(), user1.getId());
        this.channelService.joinChannel(channel1.getId(), user1.getId());
        log.info("joined? channel: {}, user: {}", channel1.getParticipants().contains(user1), user1.getChannels().contains(channel1));
        log.info("--------------------------------------------------");

        // send message test
        log.info(">> 4-3. Sending Message...");
        log.info("Target Channel ID: {}, Sender ID: {}", channel1.getId(), user1.getId());
        Message message4 = this.messageService.createMessage("Hello, this is John!", user1.getId(), channel1.getId());
        log.info("sent? channel: {}, user: {}, message: {}", channel1.getMessages().contains(message4), user1.getMessages().contains(message4), message4.getChannel().equals(channel1) && message4.getSender().equals(user1));
        log.info("--------------------------------------------------");

        // delete user test
        log.info(">> 4-4. Deleting User...");
        this.channelService.joinChannel(channel3.getId(), user3.getId());
        log.info("Target User ID: {}", user3.getId());
        this.userService.deleteUser(user3.getId());
        log.info("deleted? channel participants: {}", !channel3.getParticipants().contains(user3));
        log.info("--------------------------------------------------");

        // delete channel test
        log.info(">> 4-5. Deleting Channel...");
        this.channelService.joinChannel(channel3.getId(), user1.getId());
        Message message5 = this.messageService.createMessage("Hello, this is John!", user1.getId(), channel3.getId());
        log.info("Target Channel ID: {}", channel3.getId());
        this.channelService.deleteChannel(channel3.getId());
        try {
            this.messageService.getMessageById(message5.getId());
            log.info("deleted? user channels: {}, message: false", !user1.getChannels().contains(channel3));
        } catch (IllegalArgumentException e) {
            log.info("deleted? user channels: {}, message: true", !user1.getChannels().contains(channel3));
        }
        log.info("--------------------------------------------------");

        // delete message test
        log.info(">> 4-6. Deleting Message...");
        log.info("Target Message ID: {}", message1.getId());
        this.messageService.deleteMessage(message1.getId());
        log.info("deleted? user messages: {}, channel messages: {}", !user1.getMessages().contains(message1), !channel1.getMessages().contains(message1));
        log.info("--------------------------------------------------");

        // Additional Business Logic Test
        log.info("5. Additional Business Logic Test:");

        // duplicate channel participation test
        log.info(">> 5-1. Duplicate Channel Participation...");
        log.info("Target Channel ID: {}, User ID: {}", channel1.getId(), user1.getId());
        try {
            this.channelService.joinChannel(channel1.getId(), user1.getId());
            log.info("Duplicate participation allowed. ❌");
        } catch (IllegalArgumentException e) {
            log.info("Expected exception caught. ✅ -> {}", e.getMessage());
        }
        log.info("--------------------------------------------------");

        // empty user nickname test
        log.info(">> 5-2. Empty User Nickname...");
        try {
            this.userService.createUser("", "username", "email@codeit.com", "password", "000-000-0000");
            log.info("Empty nickname user created. ❌");
        } catch (IllegalArgumentException e) {
            log.info("Expected exception caught. ✅ -> {}", e.getMessage());
        }
        log.info("--------------------------------------------------");

        // empty user username test
        log.info(">> 5-3. Empty User Username...");
        try {
            this.userService.createUser("nickname", "", "email@codeit.com", "password", "000-000-0000");
            log.info("Empty username user created. ❌");
        } catch (IllegalArgumentException e) {
            log.info("Expected exception caught. ✅ -> {}", e.getMessage());
        }
        log.info("--------------------------------------------------");

        // duplicate username test
        log.info(">> 5-4. Duplicate Username...");
        try {
            this.userService.createUser("newuser", "johnupdated", "newemail@codeit.com", "password", "000-000-0000");
            log.info("Duplicate username user created. ❌");
        } catch (IllegalArgumentException e) {
            log.info("Expected exception caught. ✅ -> {}", e.getMessage());
        }
        log.info("--------------------------------------------------");

        // empty user email test
        log.info(">> 5-5. Empty User Email...");
        try {
            this.userService.createUser("nickname", "username", "", "password", "000-000-0000");
            log.info("Empty email user created. ❌");
        } catch (IllegalArgumentException e) {
            log.info("Expected exception caught. ✅ -> {}", e.getMessage());
        }
        log.info("--------------------------------------------------");

        // invalid email format test
        log.info(">> 5-6. Invalid Email Format...");
        try {
            this.userService.createUser("nickname", "username", "invalid-email", "password", "000-000-0000");
            log.info("Invalid email user created. ❌");
        } catch (IllegalArgumentException e) {
            log.info("Expected exception caught. ✅ -> {}", e.getMessage());
        }
        log.info("--------------------------------------------------");

        // duplicate email test
        log.info(">> 5-7. Duplicate Email...");
        try {
            this.userService.createUser("newuser", "newusername", "johnupdated@codeit.com", "password", "000-000-0000");
            log.info("Duplicate email user created. ❌");
        } catch (IllegalArgumentException e) {
            log.info("Expected exception caught. ✅ -> {}", e.getMessage());
        }
        log.info("--------------------------------------------------");

        // empty user password test
        log.info(">> 5-8. Empty User Password...");
        try {
            this.userService.createUser("nickname", "username", "email@codeit.com", "", "000-000-0000");
            log.info("Empty password user created. ❌");
        } catch (IllegalArgumentException e) {
            log.info("Expected exception caught. ✅ -> {}", e.getMessage());
        }
        log.info("--------------------------------------------------");

        // invalid password length test
        log.info(">> 5-9. Invalid Password Length...");
        try {
            this.userService.createUser("nickname", "username", "email@codeit.com", "short", "000-000-0000");
            log.info("Invalid password user created. ❌");
        } catch (IllegalArgumentException e) {
            log.info("Expected exception caught. ✅ -> {}", e.getMessage());
        }
        log.info("--------------------------------------------------");

        // empty user phone number test
        log.info(">> 5-10. Empty User PhoneNumber...");
        try {
            this.userService.createUser("nickname", "username", "email@codeit.com", "password", "");
            log.info("Empty phone number user created. ❌");
        } catch (IllegalArgumentException e) {
            log.info("Expected exception caught. ✅ -> {}", e.getMessage());
        }
        log.info("--------------------------------------------------");

        // invalid phone number format test
        log.info(">> 5-11. Invalid PhoneNumber Format...");
        try {
            this.userService.createUser("nickname", "username", "email@codeit.com", "password", "123");
            log.info("Invalid phone number user created. ❌");
        } catch (IllegalArgumentException e) {
            log.info("Expected exception caught. ✅ -> {}", e.getMessage());
        }
        log.info("--------------------------------------------------");

        // empty channel name test
        log.info(">> 5-12. Empty Channel Name...");
        try {
            this.channelService.createChannel("");
            log.info("Empty channel created. ❌");
        } catch (IllegalArgumentException e) {
            log.info("Expected exception caught. ✅ -> {}", e.getMessage());
        }
        log.info("--------------------------------------------------");

        // duplicate channel name test
        log.info(">> 5-13. Duplicate Channel Name...");
        try {
            this.channelService.createChannel("JavaStudyUpdated");
            log.info("Duplicate channel name created. ❌");
        } catch (IllegalArgumentException e) {
            log.info("Expected exception caught. ✅ -> {}", e.getMessage());
        }
        log.info("--------------------------------------------------");

        // empty message content test
        log.info(">> 5-14. Empty Message Content...");
        try {
            this.messageService.createMessage("", user1.getId(), channel1.getId());
            log.info("Empty message created. ❌");
        } catch (IllegalArgumentException e) {
            log.info("Expected exception caught. ✅ -> {}", e.getMessage());
        }
        log.info("--------------------------------------------------");

        // non-existent user id test
        log.info(">> 5-15. Non-existent User ID...");
        try {
            this.messageService.createMessage("Test message", java.util.UUID.randomUUID(), channel1.getId());
            log.info("Message with non-existent user created. ❌");
        } catch (IllegalArgumentException e) {
            log.info("Expected exception caught. ✅ -> {}", e.getMessage());
        }
        log.info("--------------------------------------------------");

        // non-existent channel id test
        log.info(">> 5-16. Non-existent Channel ID...");
        try {
            this.messageService.createMessage("Test message", user1.getId(), java.util.UUID.randomUUID());
            log.info("Message with non-existent channel created. ❌");
        } catch (IllegalArgumentException e) {
            log.info("Expected exception caught. ✅ -> {}", e.getMessage());
        }
        log.info("--------------------------------------------------");

        // sender not participating in channel test
        log.info(">> 5-17. Sender Not Participating in Channel...");
        Channel channel4 = this.channelService.createChannel("NotJoinedChannel");
        log.info("Target Channel ID: {}, Sender ID: {}", channel4.getId(), user1.getId());
        try {
            this.messageService.createMessage("Test message", user1.getId(), channel4.getId());
            log.info("Message sent without channel participation. ❌");
        } catch (IllegalArgumentException e) {
            log.info("Expected exception caught. ✅ -> {}", e.getMessage());
        }
        log.info("--------------------------------------------------");

        log.info("Discodeit Service Test Finished. ✅");
        log.info("--------------------------------------------------");
    }
}
