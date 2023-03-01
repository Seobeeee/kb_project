package kakaobank.seobee.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 카카오 API 조회시 받게 될 데이터
 */
@Data
@Builder
public class KakaoBlogData {
    String blogName;
    String contents;
    LocalDateTime dateTime;
    String thumbnail;
    String title;
    String url;
}
