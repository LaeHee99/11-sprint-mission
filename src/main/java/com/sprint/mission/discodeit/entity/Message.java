package com.sprint.mission.discodeit.entity;
import com.sprint.mission.discodeit.util.StringUtil;

import java.io.Serial;
import java.util.UUID;

public class Message extends BaseEntity{

    @Serial
    private static final long serialVersionUID = 1L;

    private String content;
    private UUID channelId;
    private UUID senderId;

    //생성자
    public Message(String content, UUID channelId, UUID senderId){
        super();
        this.content = content;
        this.channelId = channelId;
        this.senderId = senderId;
    }

    //getter
    public String getContent(){
        return content;
    }
    public UUID getChannelId(){
        return channelId;
    }
    public UUID getSenderId(){
        return senderId;
    }

    //업데이트 메소드
    public void update(String content, UUID channelId, UUID senderId) {//메시지 내용 수정?
        if(StringUtil.isValid(content) && channelId != null && senderId != null){ //유효한지 검사
            this.content = content;
            this.channelId = channelId;
            this.senderId = senderId;

            updateTime(); // 업데이트 시간 갱신
        } else System.out.println("메시지 정보를 갱신에 적절하지 않은 값이 있습니다.");
        return;
    }

    @Override
    public String toString(){
        return "유저ID: "+senderId+"\n채널ID: "+channelId+"\n내용: "+content+"\n";// 임시
    }
}
