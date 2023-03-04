package kakaobank.seobee.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kakaobank.seobee.dto.response.BlogSearchResponse;
import kakaobank.seobee.dto.response.SearchRankingResponse;
import kakaobank.seobee.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/blog/search")
    public BlogSearchResponse getBlogInfo(@RequestParam("query") String query,
                                          @RequestParam(value = "sort", defaultValue = "accuracy") String sort,
                                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                                          @RequestParam(value = "size", defaultValue = "20") Integer size) throws JsonProcessingException {
        return searchService.getBlogInfo(query, sort, page, size);
    }

    @GetMapping("/search/ranking")
    public SearchRankingResponse getSearchRank(){
        return searchService.getSearchRank();
    }
}
