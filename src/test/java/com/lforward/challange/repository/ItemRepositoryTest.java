package com.lforward.challange.repository;

import com.lforward.challange.model.entity.Category;
import com.lforward.challange.model.entity.Item;
import com.lforward.challange.utils.TestUtils;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

import static com.lforward.challange.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author bashir
 * @since 19/10/21.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void itemIsCreatedThenRetrievedThenDeleted() {
        Item item1 = TestUtils.createMockItem(1);
        Item item2 = TestUtils.createMockItem(2);

        assertThat(itemRepository.findAll()).hasSize(BASE_ITEM_COUNT);

        assertThat(em.contains(item1)).isFalse();
        assertThat(em.contains(item2)).isFalse();

        itemRepository.saveAll(List.of(item1, item2));

        assertThat(em.contains(item1)).isTrue();
        assertThat(em.contains(item2)).isTrue();

        flushAndDetach(item1);
        flushAndDetach(item2);

        assertThat(em.contains(item1)).isFalse();

        List<Item> items = itemRepository.findAll();

        assertThat(items).hasSize(BASE_ITEM_COUNT + 2);

        itemRepository.deleteAll(items);
        assertThat(categoryRepository.findAll()).hasSize(BASE_CATEGORY_COUNT);
    }

    @Test
    public void itemsCreatedCategoryAddedCategoryDeletedThenItemDeleted() {
        Item item1 = TestUtils.createMockItem(1);
        Item item2 = TestUtils.createMockItem(2);

        Category category1 = TestUtils.createMockCategory(1);
        Category category2 = TestUtils.createMockCategory(2);
        item1.getCategoryList().add(category1);

        categoryRepository.saveAll(List.of(category1, category2));
        itemRepository.saveAll(List.of(item1, item2));

        flushAndDetach(item1, item2, category1);

        item1 = itemRepository.findItemByUuid(appendOffset(1, TEST_ITEM_UUID)).orElseThrow();
        assertThat(item1.getCategoryList()).hasSize(1);
        assertThat(item1.getCategoryList()).extracting(Category::getName)
                .contains(appendOffset(1, TEST_CATEGORY_NAME), Index.atIndex(0));

        item1.getCategoryList().removeIf(c -> c.getUuid().equals(appendOffset(1, TEST_CATEGORY_UUID)));
        item2.getCategoryList().addAll(List.of(category1, category2));

        itemRepository.saveAll(List.of(item1, item2));

        flushAndDetach(item1, item2, category1, category2);

        item1 = itemRepository.findItemByUuid(appendOffset(1, TEST_ITEM_UUID)).orElseThrow();
        item2 = itemRepository.findItemByUuid(appendOffset(2, TEST_ITEM_UUID)).orElseThrow();

        assertThat(item1.getCategoryList()).hasSize(0);
        assertThat(item2.getCategoryList()).hasSize(2);
    }

    private void flushAndDetach(Object... object) {
        em.flush();
        Arrays.stream(object).forEach(em::detach);
    }

}
