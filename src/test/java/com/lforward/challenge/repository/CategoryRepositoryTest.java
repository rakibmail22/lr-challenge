package com.lforward.challenge.repository;

import com.lforward.challenge.model.entity.Attribute;
import com.lforward.challenge.model.entity.Category;
import com.lforward.challenge.utils.TestUtils;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import static com.lforward.challenge.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author bashir
 * @since 19/10/21.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void categoryIsCreatedThenRetrievedThenDeleted() {
        int offset = 1;
        Category category = TestUtils.createMockCategory(offset);

        assertThat(categoryRepository.findAll()).hasSize(BASE_CATEGORY_COUNT);

        assertThat(em.contains(category)).isFalse();

        category = categoryRepository.save(category);

        assertThat(em.contains(category)).isTrue();

        flushAndDetach(category);

        assertThat(em.contains(category)).isFalse();

        Category retrievedCategory = categoryRepository.findCategoryByUuid(appendOffset(offset, TEST_CATEGORY_UUID)).orElse(null);

        assertThat(categoryRepository.findAll()).hasSize(BASE_CATEGORY_COUNT + 1);
        assertThat(retrievedCategory).isNotNull();
        assertThat(retrievedCategory.getName()).isEqualTo(appendOffset(offset, TEST_CATEGORY_NAME));
        assertThat(retrievedCategory.getAttributeList()).hasSize(0);

        categoryRepository.delete(retrievedCategory);
        assertThat(categoryRepository.findAll()).hasSize(BASE_CATEGORY_COUNT);
    }

    @Test
    public void categoryIsUpdatedThenRetrieved() {
        int offset = 1;
        Category category = saveCategoryWithAttribute(offset);

        flushAndDetach(category);

        Category retrievedCategory = categoryRepository.findCategoryByUuid(appendOffset(offset, TEST_CATEGORY_UUID)).orElse(null);

        flushAndDetach(category);

        assertThat(retrievedCategory).isNotNull();

        retrievedCategory.setName(TEST_CATEGORY_NAME_UPDATED);

        retrievedCategory = categoryRepository.save(retrievedCategory);

        assertThat(retrievedCategory.getAttributeList()).hasSize(2);
        assertThat(retrievedCategory.getAttributeList())
                .extracting(Attribute::getValue)
                .contains(appendOffset(offset, TEST_ATTR_NAME_2), Index.atIndex(1));
    }

    @Test
    public void categoryAttributeIsRemoved() {
        int offset = 1;
        Category category = saveCategoryWithAttribute(offset);

        flushAndDetach(category);

        category.getAttributeList().removeIf(attr -> attr.getUuid().equals(appendOffset(offset, TEST_ATTR_2_UUID)));

        assertThat(category.getAttributeList()).hasSize(1);

        categoryRepository.save(category);

        flushAndDetach(category);

        Category retrievedCategory = categoryRepository.findCategoryByUuid(appendOffset(offset, TEST_CATEGORY_UUID)).orElse(null);
        assertThat(retrievedCategory).isNotNull();
        assertThat(retrievedCategory.getAttributeList()).hasSize(1);
        assertThat(retrievedCategory.getAttributeList())
                .extracting(Attribute::getUuid)
                .contains(appendOffset(offset, TEST_ATTR_1_UUID), Index.atIndex(0));
    }

    private Category saveCategoryWithAttribute(int offset) {
        Category category = TestUtils.createMockCategory(offset);
        category.getAttributeList().addAll(createMockAttribute(offset));

        return categoryRepository.save(category);
    }

    private void flushAndDetach(Category category) {
        em.flush();
        em.detach(category);
    }

}