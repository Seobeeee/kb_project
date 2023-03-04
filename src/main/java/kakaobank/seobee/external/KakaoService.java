package kakaobank.seobee.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import kakaobank.seobee.assembler.BlogDataAssembler;
import kakaobank.seobee.dto.CommonAPIResult;
import kakaobank.seobee.dto.KakaoAPIResult;
import kakaobank.seobee.utils.Constant;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.stream.Collectors;

@Slf4j
@Service
@NoArgsConstructor
public class KakaoService {

    @Value("${api.kakao}")
    private String apiUrl;

    @Value("${secret.kakao}")
    private String secretKey;

    /**
     * API Call 이 에러되었을 경우, CommonAPIResult의 Response값을 에러로 세팅해준다.
     * @param query
     * @param sort
     * @param page
     * @param size
     * @return
     * @throws JsonProcessingException
     */
    public CommonAPIResult searchBlogInfo(String query, String sort, Integer page, Integer size) throws JsonProcessingException {
        return WebClient.create(apiUrl).get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", query)
                        .queryParam("sort", sort)
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .header("Authorization", secretKey)
                .retrieve()
                .bodyToMono(KakaoAPIResult.class)
                .map(response -> {
                    var blogDatas = response.getDocuments().stream()
                            .map(BlogDataAssembler::toBlogData)
                            .collect(Collectors.toList());

                    return CommonAPIResult.builder()
                            .resultCode(Constant.ResultCode.SUCCESS.getResultCode())
                            .blogDatas(blogDatas)
                            .build();
                })
                .onErrorReturn(CommonAPIResult.builder()
                        .resultCode(Constant.ResultCode.API_CALL_ERROR.getResultCode())
                        .build())
                .block();
    }
}
