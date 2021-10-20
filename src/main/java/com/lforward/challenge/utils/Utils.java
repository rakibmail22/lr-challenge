package com.lforward.challenge.utils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author bashir
 * @since 19/10/21.
 */
public class Utils {

    public static final int ITEM_PER_PAGE = 20;
    public static final String DELIM_SPACE = " ";

    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }

    public static <T> List<T> nullSafeList(List<T> list) {
        return Optional.ofNullable(list).orElseGet(Collections::emptyList);
    }
}
