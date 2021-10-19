package com.lforward.challange.repository;

import com.lforward.challange.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author bashir
 * @since 19/10/21.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findItemByUuid(String uuid);
}
