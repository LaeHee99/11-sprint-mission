# =========================================================
# Build Stage
# ---------------------------------------------------------
# 애플리케이션을 실행 가능한 Spring Boot jar로 빌드하는 단계임
# 요구사항에 따라 Amazon Corretto 17 이미지를 기반 이미지로 사용함
# =========================================================
FROM amazoncorretto:17 AS build

# 컨테이너 내부 작업 디렉터리를 /app으로 설정함
WORKDIR /app

# Gradle Wrapper와 Gradle 설정 파일을 먼저 복사함
# gradlew는 Linux/Mac용 실행 파일이고, gradlew.bat은 Windows용 실행 파일임
COPY gradlew gradlew.bat settings.gradle build.gradle ./

# Gradle Wrapper 실행에 필요한 wrapper 파일들을 복사함
COPY gradle ./gradle

# 애플리케이션 소스 코드를 복사함
COPY src ./src

# Linux 컨테이너에서 gradlew를 실행할 수 있도록 실행 권한 부여함
RUN chmod +x ./gradlew

# Gradle Wrapper로 Spring Boot 실행 jar 생성함
# Docker 이미지 빌드 단계에서는 빠른 빌드를 위해 테스트 제외함
RUN ./gradlew clean bootJar -x test

# =========================================================
# Runtime Stage
# ---------------------------------------------------------
# 빌드 결과물인 jar 파일만 포함해서 애플리케이션을 실행하는 단계임
# 빌드 도구나 소스 코드를 포함하지 않아 이미지 크기를 줄일 수 있음
# =========================================================
FROM amazoncorretto:17

# 실행 컨테이너의 작업 디렉터리를 /app으로 설정함
WORKDIR /app

# 실행할 jar 파일 이름을 구성하기 위한 프로젝트 이름임
ENV PROJECT_NAME=discodeit

# 실행할 jar 파일 이름을 구성하기 위한 프로젝트 버전임
ENV PROJECT_VERSION=1.2-M8

# JVM 실행 옵션을 외부에서 주입할 수 있도록 환경 변수로 둠
# 예: -Xmx512m -Xms256m
ENV JVM_OPTS=""

# build stage에서 생성된 jar 파일만 runtime stage로 복사함
COPY --from=build /app/build/libs/${PROJECT_NAME}-${PROJECT_VERSION}.jar ./

# 컨테이너가 80 포트를 사용한다는 것을 명시함
# 실제 서버 포트는 SERVER_PORT 환경 변수나 설정 파일로 맞춰야 함
EXPOSE 80

# 컨테이너 시작 시 애플리케이션 실행함
# sh -c를 사용해야 환경 변수가 정상적으로 해석됨
ENTRYPOINT ["sh", "-c", "java $JVM_OPTS -jar /app/${PROJECT_NAME}-${PROJECT_VERSION}.jar"]