package com.lforward.challange.repository;

import com.lforward.challange.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author bashir
 * @since 19/10/21.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findCategoryByUuid(String uuid);

    List<Category> findCategoryByUuidIn(List<String> uuidList);

    void deleteAllByUuidIn(List<String> uuidList);
}
