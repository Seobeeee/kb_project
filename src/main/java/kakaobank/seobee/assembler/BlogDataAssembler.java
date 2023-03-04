package kakaobank.seobee.assembler;

import kakaobank.seobee.dto.BlogData;
import kakaobank.seobee.dto.KakaoBlogData;
import kakaobank.seobee.dto.NaverBlogData;
import lombok.experimental.UtilityClass;

/**
 * 카카오 및 네이버에서 블로그 검색시 조회될 데이터를 토대로,
 * 공통 데이터인(BlogData)를 만들어 주기 위한 어샘블러.
 */
@UtilityClass
public class BlogDataAssembler {

    /**
     * 카카오 블로그 데이터에서 공통 데이터로 변환
     * @param kakaoBlogData 카카오 블로그 검색시 얻게 될 데이터
     * @return  카카오 및 네이버 블로그 공통 데이터
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

    /**
     * 네이버 블로그 데이터에서 공통 데이터로 변환하기 위한 어셈블러.
     * @param naverBlogData
     * @return
     */
    public BlogData toBlogData(NaverBlogData naverBlogData){
        return BlogData.builder()
                .blogName(naverBlogData.getBloggerName())
                .contents(naverBlogData.getDescription())
                .dateTime(naverBlogData.getPostDate())
                .title(naverBlogData.getTitle())
                .url(naverBlogData.getLink())
                .thumbnail(naverBlogData.getBloggerLink())
                .build();
    }
}
