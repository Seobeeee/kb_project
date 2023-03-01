package kakaobank.seobee.service;

import kakaobank.seobee.dto.response.BlogSearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Slf4j
@Service
public class SearchService {

    public BlogSearchResponse getBlogInfo(String query, String sort, Integer page, Integer size){
        return BlogSearchResponse.builder().build();
    }
}
