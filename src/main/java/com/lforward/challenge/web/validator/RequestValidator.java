package com.lforward.challenge.web.validator;

import com.lforward.challenge.exception.InvalidInputSizeException;
import com.lforward.challenge.utils.Utils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author bashir
 * @since 19/10/21.
 */
@Component
public class RequestValidator {

    public static final int DEFAULT_LIST_INPUT_MAX = 10;
    public static final int DEFAULT_LIST_INPUT_MIN = 1;

    public <T> void validateListSize(List<T> list) {
        list = Utils.nullSafeList(list);
        if (list.size() < DEFAULT_LIST_INPUT_MIN || list.size() > DEFAULT_LIST_INPUT_MAX) {
            throw new InvalidInputSizeException(String.format("List size must be between %s and %s",
                    DEFAULT_LIST_INPUT_MIN,
                    DEFAULT_LIST_INPUT_MAX)
            );
        }
    }
}
