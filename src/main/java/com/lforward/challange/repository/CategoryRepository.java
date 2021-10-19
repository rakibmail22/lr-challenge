package com.lforward.challange.repository;

import com.lforward.challange.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author bashir
 * @since 19/10/21.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoryByUuid(String uuid);
}
