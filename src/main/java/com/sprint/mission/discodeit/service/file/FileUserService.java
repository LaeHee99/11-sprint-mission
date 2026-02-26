package com.sprint.mission.discodeit.service.file;
import com.sprint.mission.discodeit.entity.Domain.User;
import com.sprint.mission.discodeit.service.UserService;

import java.io.*;
import java.util.*;

public class FileUserService implements UserService {
    private Map<UUID, User> data;   // 원래는 private final map으로 저장 > 어차피 바뀌니까 final빼기
    // TODO
    // 저장(saveToFile), 불러오기 (loadFromFile) 구현
    private void saveToFile(){
        try (FileOutputStream fos = new FileOutputStream("users.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadFromFile(){
        File file = new File("users.ser");
        if (!file.exists()) return;  // 파일 없으면 그냥 넘어가기

        try (FileInputStream fis = new FileInputStream("users.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            this.data = (Map<UUID, User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public FileUserService(){
        this.data = new HashMap<>();
        loadFromFile(); // 기존 데이터 불러오게 하기
    }

    @Override
    public UUID create(User user) {
        data.put(user.getId(), user);
        saveToFile();   // 생성 후 파일에 저장
        return user.getId();
    }

    @Override
    public User read(UUID id) {
        return data.get(id);
    }       // 단건조회(key값인 id 넣기)

    @Override
    public List<User> readAll(){
        return new ArrayList<>(data.values());
    }   // 싹다 조회. 리스트로

    @Override
    public void update(UUID id, String userName, String userNickname, String userStatus) {
        User user = data.get(id);
        user.updateUserName(userName, userNickname, userStatus);
        saveToFile();   // 업데이트 후 파일에 저장
    }   // key값인 id를 입력하고 수정할 내용적기

    @Override
    public void delete(UUID id) {
        data.remove(id);
        saveToFile();   // 삭제 후 파일에 저장
    }   // 삭제(key 값인 id값이 필요함)

    @Override
    public String toString() {
        return data.toString();
    }   // 문자열 반환


}
