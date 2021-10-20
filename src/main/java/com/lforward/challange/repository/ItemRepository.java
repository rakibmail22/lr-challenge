package com.lforward.challange.repository;

import com.lforward.challange.model.entity.Category;
import com.lforward.challange.model.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author bashir
 * @since 19/10/21.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findItemByUuid(String uuid);

    List<Item> findItemByUuidIn(List<String> uuidList);

    void deleteAllByUuidIn(List<String> uuidList);

    Page<Item> findItemsByCategoryListUuid(String categoryUuid, Pageable pageable);
}
