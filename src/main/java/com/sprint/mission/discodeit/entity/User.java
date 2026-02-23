package com.sprint.mission.discodeit.entity;
import com.sprint.mission.discodeit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User extends BaseEntity{

    private String userName; //닉네임
    private String email; //이메일 or 아이디?
    private String password; // 비밀번호
    private final List<UUID> joinedChannelId = new ArrayList<>(); // 속한 채널

    //추가 필드
    //private String status;
    //private String profileImage;


    //생성자
    public User(String userName, String email, String password){
        super();
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    //getter
    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
    }
    public String getEmail(){
        return email;
    }
    public List<UUID> getJoinedChannelId(){
        return joinedChannelId;
    }


    //업데이트 메소드
    public void update(String userName, String email, String password) {//유저이름, 비밀번호
        if(StringUtil.isValid(userName) && StringUtil.isValid(email) && StringUtil.isValid(password)){ //유효한지 검사
            this.userName = userName;
            this.email = email;
            this.password = password;

            updateTime(); // 업데이트 시간 갱신
        } else System.out.println("유저 정보를 갱신에 적절하지 않은 값이 있습니다.");
        return;
    }

    @Override
    public String toString() {
        return "닉네임: "+userName+"\n이메일: "+email+"\n비밀번호: "+password;
    }

    //채널 ID받아서 속한 채널 리스트에 올리기, 리스트에서 삭제하기
    public void joinChannel(UUID channelId){
        if(channelId == null) {
            System.out.println("채널 id가 null입니다.");
            return;
        }
        if(joinedChannelId.contains(channelId)){
            System.out.println("이미 채널에 속해있습니다.");
            return;
        }
        this.joinedChannelId.add(channelId);
        updateTime();
    }
    public void leaveChannel(UUID channelId){
        if(channelId == null){
            System.out.println("채널 id가 null입니다.");
            return;
        }
        if(!joinedChannelId.contains(channelId)){
            System.out.println("이 채널에 속해있지 않습니다.");
        }
        this.joinedChannelId.remove(channelId);
        updateTime();
    }

}
