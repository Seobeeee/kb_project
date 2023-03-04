package kakaobank.seobee;

import kakaobank.seobee.config.EmbeddedRedisConfig;
import kakaobank.seobee.dto.BlogData;
import kakaobank.seobee.dto.RankData;
import kakaobank.seobee.dto.response.BlogSearchResponse;
import kakaobank.seobee.dto.response.SearchRankingResponse;
import kakaobank.seobee.service.SearchService;
import kakaobank.seobee.utils.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class SearchControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private SearchService searchService;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDoc) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                .alwaysDo(print())
                .apply(documentationConfiguration(restDoc))
                .build();
    }

    @Test
    void getBlogInfo() throws Exception {
        var result = BlogSearchResponse.builder()
                .responseCode(Constant.ResultCode.SUCCESS.getResultCode())
                .blogDataList(List.of(BlogData.builder()
                                .blogName("블로그 이름")
                                .contents("내용")
                                .dateTime(LocalDateTime.now().toString())
                                .thumbnail("썸네일 주소")
                                .title("제목")
                                .url("URL")
                        .build()))
                .build();

        var request = List.of(
                parameterWithName("query").description("검색어"),
                parameterWithName("size").description("표시 개수").optional(),
                parameterWithName("page").description("페이지").optional(),
                parameterWithName("sort").description("정렬방식").optional()
        );

        var response = List.of(
                fieldWithPath("responseCode").type(JsonFieldType.NUMBER).description("응답 코드"),
                fieldWithPath("blogDataList[].blogName").type(JsonFieldType.STRING).description("블로그 이름"),
                fieldWithPath("blogDataList[].contents").type(JsonFieldType.STRING).description("내용"),
                fieldWithPath("blogDataList[].dateTime").type(JsonFieldType.STRING).description("시간"),
                fieldWithPath("blogDataList[].thumbnail").type(JsonFieldType.STRING).description("썸네일 주소"),
                fieldWithPath("blogDataList[].title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("blogDataList[].url").type(JsonFieldType.STRING).description("URL")
        );

        doReturn(result).when(searchService).getBlogInfo(anyString(), anyString(), anyInt(), anyInt());

        this.mockMvc.perform(get("/blog/search")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("query", "집"))
                .andExpect(status().isOk())
                .andDo(document("GetBlogInfo",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(response),
                        requestParameters(request)
                ));
    }

    @Test
    void getSearchRanking() throws Exception {
        var result = SearchRankingResponse.builder()
                .responseCode(Constant.ResultCode.SUCCESS.getResultCode())
                .rankDataList(List.of(RankData.builder()
                                .key("검색어")
                                .score(1)
                        .build()))
                .build();

        var response = List.of(
                fieldWithPath("responseCode").type(JsonFieldType.NUMBER).description("응답 코드"),
                fieldWithPath("rankDataList[].key").type(JsonFieldType.STRING).description("검색어"),
                fieldWithPath("rankDataList[].score").type(JsonFieldType.NUMBER).description("검색 횟수")
        );

        doReturn(result).when(searchService).getSearchRank();

        this.mockMvc.perform(get("/search/ranking")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("GetSearchRanking",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(response)
                ));
    }
}
