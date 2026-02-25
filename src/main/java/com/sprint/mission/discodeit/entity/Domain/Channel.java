package com.sprint.mission.discodeit.entity.Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Channel extends BaseEntity implements Serializable {
    private String channelName;     // 채널이름(채널명)
    private String channelDescription;      // 채널주소
    private static final long serialVersionUID = 1L;
    private List<UUID> memberIds = new ArrayList<>();   // 채널에 있는 유저 목록 id로 관리

    public Channel(String channelName, String channelDescription){  // 채널을 새로만듬(이름, 주소)
        super();    // 유효아이디, 만든시간 업데이트
        this.channelName = channelName;
        this.channelDescription = channelDescription;
    }
    public String getChannelName() {return channelName;}    // 채널이름 리턴하기
    public String getChannelDescription() {return channelDescription;}      // 채널 주소 리턴

    public void updateChannel(String channelName, String channelDescription) {
        this.channelName = channelName;     // 채널 업데이트하는 설정
        this.channelDescription = channelDescription;
        updateTimestamp();  // 채널 이름 업데이트 시간 넣어주기
    }

    public List<UUID> getMemberIds() {return memberIds;}

    public void addMemberId(UUID memberId) {
        if(!memberIds.contains(memberId)) { // 중복 체크 후 중복이면 못들어오게. add하는거임.
            memberIds.add(memberId);
            updateTimestamp();
        }
    }

    public void removeMemberId(UUID memberId) { // 멤버목록 삭제하기(id 값으로 찾아서 제거)
        memberIds.remove(memberId);
        updateTimestamp();
    }

    @Override
    public String toString() {
        return
                "Channel{channelName='" + channelName + "', channelDescription='" + channelDescription + "'}\n";
    }
}
