package kakaobank.seobee.assembler;

import kakaobank.seobee.dto.BlogData;
import kakaobank.seobee.dto.KakaoBlogData;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

/**
 * 카카오 및 네이버에서 블로그 검색시 조회될 데이터를 토대로,
 * 공통 데이터인(BlogData)를 만들어 주기 위한 어샘블러.
 */
@UtilityClass
public class BlogDataAssembler {

    /**
     * 카카오 블로그 데이터에서 공통 데이터로 변환
     * @param kakaoBlogData
     * @return
     */
    public BlogData toBlogData(KakaoBlogData kakaoBlogData){
        return BlogData.builder()
                .blogName(kakaoBlogData.getBlogName())
                .contents(kakaoBlogData.getContents())
                .dateTime(kakaoBlogData.getDateTime())
                .thumbnail(kakaoBlogData.getThumbnail())
                .title(kakaoBlogData.getTitle())
                .url(kakaoBlogData.getUrl())
                .build();
    }
}
