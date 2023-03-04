package kakaobank.seobee.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 카카오 API 조회시 받게 될 데이터
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoBlogData {
    @JsonProperty("blogname")
    String blogName;
    String contents;
    @JsonProperty("datetime")
    String dateTime;
    String thumbnail;
    String title;
    String url;
}
