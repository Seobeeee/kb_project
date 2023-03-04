package kakaobank.seobee.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import kakaobank.seobee.assembler.BlogDataAssembler;
import kakaobank.seobee.dto.CommonAPIResult;
import kakaobank.seobee.dto.NaverAPIResult;
import kakaobank.seobee.utils.Constant;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class NaverService {

    @Value("${api.naver}")
    private String apiUrl;

    @Value("${secret.naver}")
    private String secretKey;

    @Value("${naver.clientId}")
    private String clientId;

    public CommonAPIResult searchBlogInfo(String query, String sort, Integer page, Integer size) throws JsonProcessingException {

        // 네이버 API 조회 양식에따른 변화
        String naverSort = "";
        if(sort.equals("accuracy")){
            naverSort = "sim";
        }
        else if(sort.equals("latest")){
            naverSort = "date";
        }

        String finalNaverSort = naverSort;
        return WebClient.create(apiUrl).get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", query)
                        .queryParam("sort", finalNaverSort)
                        .queryParam("start", page)
                        .queryParam("display", size)
                        .build())
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", secretKey)
                .retrieve()
                .bodyToMono(NaverAPIResult.class)
                .map(response -> {
                    var blogData = response.getItems().stream()
                            .map(BlogDataAssembler::toBlogData)
                            .collect(Collectors.toList());

                    return CommonAPIResult.builder()
                            .resultCode(Constant.ResultCode.SUCCESS.getResultCode())
                            .blogDatas(blogData)
                            .build();
                })
                .onErrorReturn(CommonAPIResult.builder()
                        .resultCode(Constant.ResultCode.API_CALL_ERROR.getResultCode())
                        .build())
                .block();
    }
}
