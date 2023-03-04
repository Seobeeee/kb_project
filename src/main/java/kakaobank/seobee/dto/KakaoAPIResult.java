package kakaobank.seobee.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 카카오 API 조회시 받게될 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoAPIResult {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoBlogMeta{
        @JsonProperty("is_end")
        Boolean isEnd;
        @JsonProperty("pageable_count")
        Integer pageableCount;
        @JsonProperty("total_count")
        Long totalCount;
    }

    KakaoBlogMeta meta;
    List<KakaoBlogData> documents;
}
