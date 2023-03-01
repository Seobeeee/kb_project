package kakaobank.seobee.dto.response;

import kakaobank.seobee.dto.BlogData;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BlogSearchResponse {
    Integer responseCode;
    List<BlogData> blogDataList;
}
