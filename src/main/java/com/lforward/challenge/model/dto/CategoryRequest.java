package com.lforward.challenge.model.dto;

import com.lforward.challenge.model.validation.UpdateValidationGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author bashir
 * @since 19/10/21.
 */
public record CategoryRequest(
        @NotEmpty(groups = UpdateValidationGroup.class) String uuid,
        @NotEmpty @Size(max = 128) String name,
        @Size(max = 20) List<String> attributes) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}