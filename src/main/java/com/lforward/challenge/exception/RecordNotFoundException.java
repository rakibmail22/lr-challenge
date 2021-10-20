package com.lforward.challenge.exception;

import com.lforward.challenge.utils.Utils;

/**
 * @author bashir
 * @since 19/10/21.
 */
public class RecordNotFoundException extends RuntimeException {

    private static final String DEFAULT_MSG = "Not Found";
    private final String entityName;
    private final String message;

    public RecordNotFoundException(String entityName) {
        this(entityName, DEFAULT_MSG);
    }

    public RecordNotFoundException(String entityName, String message) {
        super();
        this.entityName = entityName;
        this.message = message;
    }

    public String getMessage() {
        return String.join(Utils.DELIM_SPACE, this.entityName, this.message);
    }
}
