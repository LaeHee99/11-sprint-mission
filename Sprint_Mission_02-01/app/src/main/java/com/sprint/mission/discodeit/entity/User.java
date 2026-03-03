package com.sprint.mission.discodeit.entity;

public class User extends BaseEntity{

    private String username;
    private String password;

    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    // Getter
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Update
    // 유저명 수정
    public void updateUsername(String username) {
        this.username = username;
        touch();
    }

    // 비밀번호 수정도 있으면 좋지만 일단 TODO로 
    // - 비밀변경 수정이 들어오면 updatedAt은 유저명 변경에 종속하고 비밀번호 수정은 따로 비밀번호 업데이트 일자를 만들면 좋을 듯

}
