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
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity(name = "item")
public class Item extends Persistent {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemSeq")
    @SequenceGenerator(name = "itemSeq", sequenceName = "item_sequence", allocationSize = 1)
    @EqualsAndHashCode.Include
    private long id;

    @Column(length = 128)
    private String name;

    @ManyToMany
    @JoinTable(name = "item_category",
            joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private List<Category> categoryList;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    public Item() {
        this.categoryList = new ArrayList<>();
    }
}