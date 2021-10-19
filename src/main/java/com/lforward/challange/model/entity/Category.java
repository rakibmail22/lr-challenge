package com.lforward.challange.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bashir
 * @since 18/10/21.
 */
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "category")
public class Category extends Persistent {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorySeq")
    @SequenceGenerator(name = "categorySeq", sequenceName = "category_sequence", allocationSize = 1)
    @EqualsAndHashCode.Include
    private long id;

    @Column(length = 128, nullable = false)
    private String name;

    @ElementCollection
    @OrderColumn(name = "idx", nullable = false)
    @JoinTable(name = "category_attribute", joinColumns = {@JoinColumn(name = "category_id")})
    private List<Attribute> attributeList;

    public Category() {
        this.attributeList = new ArrayList<>();
    }
}