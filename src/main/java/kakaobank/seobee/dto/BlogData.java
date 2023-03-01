package kakaobank.seobee.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 네이버 및 카카오에서 전달받은 블로그 데이터를 토대로
 * 공통 데이터 DTO를 만들기 위함.
 */
@Data
@Builder
public class BlogData {
    String blogName;
    String contents;
    LocalDateTime dateTime;
    String thumbnail;
    String title;
    String url;
}
