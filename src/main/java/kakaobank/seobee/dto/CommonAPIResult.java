package kakaobank.seobee.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommonAPIResult {
    Integer resultCode;
    List<BlogData> blogDatas;
}
