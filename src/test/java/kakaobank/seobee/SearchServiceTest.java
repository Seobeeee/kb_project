package kakaobank.seobee;

import com.fasterxml.jackson.core.JsonProcessingException;
import kakaobank.seobee.dao.RedisService;
import kakaobank.seobee.dto.CommonAPIResult;
import kakaobank.seobee.external.KakaoService;
import kakaobank.seobee.external.NaverService;
import kakaobank.seobee.service.SearchService;
import kakaobank.seobee.utils.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    private SearchService searchService;

    @Mock private KakaoService kakaoService;
    @Mock private NaverService naverService;
    @Mock private RedisService redisService;

    @BeforeEach
    void setup(){
        this.searchService = new SearchService(kakaoService, naverService, redisService);
    }

    @Test
    void 성공_카카오블로그검색() throws JsonProcessingException {
        var apiResult = CommonAPIResult.builder()
                .resultCode(Constant.ResultCode.SUCCESS.getResultCode())
                .blogDatas(List.of())
                .build();

        doReturn(apiResult).when(kakaoService).searchBlogInfo("검색어", "accuracy", 1, 20);

        doNothing().when(redisService).setKeyword(anyString());

        var result = searchService.getBlogInfo("검색어", "accuracy", 1, 20);
        assertEquals(result.getResponseCode(), Constant.ResultCode.SUCCESS.getResultCode());
    }

    @Test
    void 성공_네이버블로그검색() throws JsonProcessingException {
        // 카카오 서버 장애로 인한 네이버 블로그 검색 진행.
        var kakaoResult = CommonAPIResult.builder()
                .resultCode(Constant.ResultCode.API_CALL_ERROR.getResultCode())
                .blogDatas(List.of())
                .build();
        doReturn(kakaoResult).when(kakaoService).searchBlogInfo("검색어", "accuracy", 1, 20);

        var naverResult = CommonAPIResult.builder()
                .resultCode(Constant.ResultCode.SUCCESS.getResultCode())
                .blogDatas(List.of())
                .build();
        doReturn(naverResult).when(naverService).searchBlogInfo("검색어", "accuracy", 1, 20);

        var result = searchService.getBlogInfo("검색어", "accuracy", 1, 20);
        assertEquals(result.getResponseCode(), Constant.ResultCode.SUCCESS.getResultCode());
    }

    @Test
    void 실패_모든블로그검색실패() throws JsonProcessingException {
        // 카카오 서버 장애로 인한 네이버 블로그 검색 진행.
        var kakaoResult = CommonAPIResult.builder()
                .resultCode(Constant.ResultCode.API_CALL_ERROR.getResultCode())
                .blogDatas(List.of())
                .build();
        doReturn(kakaoResult).when(kakaoService).searchBlogInfo("검색어", "accuracy", 1, 20);

        var naverResult = CommonAPIResult.builder()
                .resultCode(Constant.ResultCode.API_CALL_ERROR.getResultCode())
                .blogDatas(List.of())
                .build();
        doReturn(kakaoResult).when(naverService).searchBlogInfo("검색어", "accuracy", 1, 20);

        var result = searchService.getBlogInfo("검색어", "accuracy", 1, 20);
        assertEquals(result.getResponseCode(), Constant.ResultCode.API_CALL_ERROR.getResultCode());
    }

    @Test
    void 실패_페이지최대값초과() throws JsonProcessingException {
        var result = searchService.getBlogInfo("검색어", "accuracy", 51, 20);
        assertEquals(result.getResponseCode(), Constant.ResultCode.MAX_PAGE_OVER.getResultCode());
    }

    @Test
    void 실패_사이즈최대값초과() throws JsonProcessingException {
        var result = searchService.getBlogInfo("검색어", "accuracy", 11, 51);
        assertEquals(result.getResponseCode(), Constant.ResultCode.MAX_SIZE_OVER.getResultCode());
    }
}
