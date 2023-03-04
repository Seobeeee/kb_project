package kakaobank.seobee.dto.response;

import kakaobank.seobee.dto.RankData;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchRankingResponse {
    Integer responseCode;
    List<RankData> rankDataList;
}
