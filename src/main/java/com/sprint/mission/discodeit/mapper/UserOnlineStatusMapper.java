package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.security.DiscodeitUserDetails;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserOnlineStatusMapper {

  private final SessionRegistry sessionRegistry;

  @Named("online")
  public Boolean online(User user) {
    // User가 없으면 온라인 상태가 아니라고 판단함
    if (user == null) {
      return false;
    }

    // SessionRegistry에 등록된 인증 사용자 중 같은 userId를 가진 principal이 있는지 확인함
    return sessionRegistry.getAllPrincipals().stream()
        .filter(principal -> principal instanceof DiscodeitUserDetails)
        .map(principal -> (DiscodeitUserDetails) principal)
        .filter(userDetails -> userDetails.getId().equals(user.getId()))

        // 만료되지 않은 세션이 하나라도 있으면 온라인으로 판단함
        .anyMatch(userDetails -> !sessionRegistry.getAllSessions(userDetails, false).isEmpty());
  }
}