package com.lforward.challange.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author bashir
 * @since 18/10/21.
 */
@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Persistent implements Serializable {

    @Column(length = 64, nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private String uuid;

    private LocalDateTime created;

    private LocalDateTime updated;

    @Version
    private int version;
}
