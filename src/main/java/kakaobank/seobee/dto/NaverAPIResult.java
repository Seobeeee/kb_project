package kakaobank.seobee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 네이버 API 조회시 받게될 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaverAPIResult {
    String lastBuildDate;
    Integer total;
    Integer start;
    Integer display;
    List<NaverBlogData> items;
}
