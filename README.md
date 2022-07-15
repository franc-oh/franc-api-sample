<div align="center">
    <h1>Rest-Api Sample</h1>
</div>

## 1. 설명
- 나만의 표준 API-어플리케이션
- 어플리케이션을 개발함에 있어 필요한 다양한 모범사례를 적용
- 언제든 참고가 가능하도록 나만의 샘플을 만든다.

### 회원의 멤버십을 관리하는 어플리케이션
- 회원은 본인이 원하는 멤버십에 가입하여, 포인트를 적립할 수 있다.
- 또한 원하면 멤버십 탈퇴도 가능, 하지만 탈퇴 당일 재가입은 불가하다.
- 포인트 적립은 바코드를 통해 적립하는 상황을 가정, Header의 바코드 번호로 식별하여 적립한다.
- 서비스 개발에 대한 상세 내용은 <b>'DEVLOG.md'</b>에 정리

## 2. 환경
- JDK 1.8
- Spring Boot 2.3.7.RELEASE
- Spring Data JPA
- Spring Boot Test
- JUnit & Mockito
- lombok
- h2-db
- Gradle 7.4

## 3. 설정
- profile='local', local_port=8080

## 4. 사용법
1. Install
    - `$ git clone https://github.com/franc-oh/franc-api-sample.git`
    
2. Build
    - `$ ./gradlew build`
    - 빌드 시 'Permission dinied' 발생한 경우
        - `$. chmod +x ./gradlew` 후 다시 빌드
        
3. Execute
    - `$ java -jar ./build/libs/franc-api-sample-1.0-SNAPSHOT.jar`
    
