package kakaobank.seobee.dao;

import kakaobank.seobee.dto.RankData;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RedisService {

    private final RedisTemplate redisTemplate;

    private static String RANK_KEY = "ranking";

    /**
     * 인기검색어에 등록하기 위한 함수.
     * @param query
     */
    public void setKeyword(String query) {
        redisTemplate.opsForZSet().incrementScore(RANK_KEY, query, 1);
    }

    public List<RankData> getTop10ValueWithScore(){
        return (List<RankData>) redisTemplate.opsForZSet().reverseRangeWithScores("ranking", 0, 10)
                .stream()
                .map(result -> RankData.builder()
                        .score(((DefaultTypedTuple) result).getScore().intValue())
                        .key(((DefaultTypedTuple) result).getValue().toString())
                        .build())
                .collect(Collectors.toList());
    }
}
