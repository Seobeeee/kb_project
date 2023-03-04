package kakaobank.seobee.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import kakaobank.seobee.dto.CommonAPIResult;
import kakaobank.seobee.dto.RankData;
import kakaobank.seobee.dto.response.BlogSearchResponse;
import kakaobank.seobee.dto.response.SearchRankingResponse;
import kakaobank.seobee.external.KakaoService;
import kakaobank.seobee.external.NaverService;
import kakaobank.seobee.utils.Constant.ResultCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class SearchService {
    private final KakaoService kakaoService;
    private final NaverService naverService;

    private final RedisTemplate redisTemplate;

    private static Integer MAX_PAGE = 50;
    private static Integer MAX_SIZE = 50;

    /**
     * 블로그 정보를 조회하기 위한 API
     * @param query
     * @param sort
     * @param page
     * @param size
     * @return
     */
    public BlogSearchResponse getBlogInfo(String query, String sort, Integer page, Integer size) throws JsonProcessingException {
        // Max page 예외처리(카카오 - 50, 네이버 - 100 이지만, 카카오에 맞춰 50으로 진행 )
        if(page > MAX_PAGE){
            return BlogSearchResponse.builder()
                    .responseCode(ResultCode.MAX_PAGE_OVER.getResultCode())
                    .build();
        }

        // Max size 예외처리(카카오 - 50, 네이버 - 100 이지만, 카카오에 맞춰 50으로 진행 )
        if(size > MAX_SIZE){
            return BlogSearchResponse.builder()
                    .responseCode(ResultCode.MAX_SIZE_OVER.getResultCode())
                    .build();
        }

        // sort 예외처리
        var sorts = List.of("accuracy", "latest");
        if(!sorts.contains(sort)){
            return BlogSearchResponse.builder()
                    .responseCode(ResultCode.WRONG_SORT.getResultCode())
                    .build();
        }

        // API 조회
        var result = this.searchBlog(query, sort, page, size);

        // 인기검색어로 등록
        this.setKeyword(query);

        return BlogSearchResponse.builder()
                .responseCode(ResultCode.SUCCESS.getResultCode())
                .blogDataList(result.getBlogDatas())
                .build();
    }

    /**
     * 첫 시도에 카카오 API를 호출하고, 해당 API가 실패 할 경우 네이버를 호출한다.
     * 네이버도 실패할 경우, 에러를 반환.
     * @param query
     * @param sort
     * @param page
     * @param size
     * @return
     */
    private CommonAPIResult searchBlog(String query, String sort, Integer page, Integer size) throws JsonProcessingException {

        // 카카오쪽으로 먼저 API호출 하여 데이터를 가져온다.
        var kakaoResult = kakaoService.searchBlogInfo(query, sort, page, size);
        if(kakaoResult.getResultCode().equals(ResultCode.SUCCESS.getResultCode())){
            return kakaoResult;
        }

        // 카카오 API가 실패하였을 경우 네이버 API로 호출 진행한다.
        var naverResult = naverService.searchBlogInfo(query, sort, page, size);
        if(naverResult.getResultCode().equals(ResultCode.SUCCESS.getResultCode())){
            return naverResult;
        }
        
        // 이외의 경우 에러로 반환
        return CommonAPIResult.builder()
                .resultCode(ResultCode.API_CALL_ERROR.getResultCode())
                .build();
    }

    /**
     * 인기검색어에 등록하기 위한 함수.
     * @param query
     */
    private void setKeyword(String query){
        redisTemplate.opsForZSet().incrementScore("ranking", query, 1);

        /*
        var score = redisTemplate.opsForZSet().reverseRangeWithScores("ranking", 0, 100);

        var text = score.stream().map(temp -> {
            System.out.println(temp);
            var dataScore = ((DefaultTypedTuple) temp).getScore().intValue();
            var value = ((DefaultTypedTuple) temp).getValue();
            return temp;
        }).collect(Collectors.toList());
        System.out.println(score);
         */
    }

    public SearchRankingResponse getSearchRank(){
        var rankData = redisTemplate.opsForZSet().reverseRangeWithScores("ranking", 0, 10)
                .stream()
                .map(result -> RankData.builder()
                        .score(((DefaultTypedTuple) result).getScore().intValue())
                        .key(((DefaultTypedTuple) result).getValue().toString())
                        .build())
                .collect(Collectors.toList());

        return SearchRankingResponse.builder()
                .responseCode(ResultCode.SUCCESS.getResultCode())
                .rankDataList((List<RankData>) rankData)
                .build();
    }
}
