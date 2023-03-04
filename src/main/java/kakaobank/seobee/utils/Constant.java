package kakaobank.seobee.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Constant {

    @Getter
    @AllArgsConstructor
    public enum ResultCode{
        SUCCESS(1),

        MAX_PAGE_OVER(101),
        MAX_SIZE_OVER(102),
        WRONG_SORT(103),
        SERVER_BUSY(104),
        API_CALL_ERROR(105);

        private final Integer resultCode;
    }
}
