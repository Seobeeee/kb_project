package kakaobank.seobee.controller;

import kakaobank.seobee.dto.response.BlogSearchResponse;
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
                                          @RequestParam(value="size", defaultValue = "20") Integer size){
        return searchService.getBlogInfo(query, sort, page, size);
    }
}
