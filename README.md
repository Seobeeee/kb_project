# kb_project

안녕하세요.

해당 프로젝트의 열람 권한은 종료되는대로 private으로 변경 진행하도록 하겠습니다.

감사합니다.

---

## jar파일 다운로드 경로
두 경로 모두 같은 파일입니다. 하나만 다운로드 받으시면 됩니다.
1. https://github.com/Seobeeee/kb_project/raw/main/kakaobank.jar
2. https://drive.google.com/file/d/1uaL-iWPVKtB3hXVRqc0AA1HQf3q1v1Wu/view?usp=share_link

## API 명세서 경로
API 명세서의 경우 두가지 조회 방법이 존재합니다.

1. <font color=red>(추천)</font> jar파일 실행 후 -> http://localhost:8080/docs/restdoc.html
2. 파일로 보기 -> https://github.com/Seobeeee/kb_project/blob/main/restdoc.html

---

## 사용한 플러그인

### <font color=red>WebClient (API 호출)</font>
	org.springframework.boot:spring-boot-starter-webflux


### <font color = red>Embedded Redis 핸들링</font>
	org.springframework.boot:spring-boot-starter-data-jpa
    org.springframework.boot:spring-boot-starter-data-redis
    group: 'it.ozimov', name: 'embedded-redis', version: '0.7.1'

### <font color = red>Restdoc</font>
	org.springframework.restdocs:spring-restdocs-mockmvc

### <font color = red>Unittest</font>
	implementation 'org.springframework.boot:spring-boot-starter-test'
