package com.lforward.challange.model.dto;

import com.lforward.challange.model.entity.ItemType;
import com.lforward.challange.model.validation.UpdateValidationGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author bashir
 * @since 20/10/21.
 */
public record ItemRequest(
        @NotEmpty(groups = UpdateValidationGroup.class) String uuid,
        @NotEmpty @Size(max = 128) String name,
        ItemType itemType,
        @Size(max = 20) List<String> categoryUuids) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
