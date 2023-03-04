package kakaobank.seobee.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaverBlogData {
    String title;
    String link;
    String description;

    @JsonProperty("bloggername")
    String bloggerName;
    @JsonProperty("bloggerlink")
    String bloggerLink;
    @JsonProperty("postdate")
    String postDate;
}
