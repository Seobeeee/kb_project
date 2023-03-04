package kakaobank.seobee.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RankData {
    String key;
    Integer score;
}
