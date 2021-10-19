package com.lforward.challange.utils;

import com.lforward.challange.model.entity.Attribute;
import com.lforward.challange.model.entity.Category;
import com.lforward.challange.model.entity.Item;
import com.lforward.challange.model.entity.ItemType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author bashir
 * @since 19/10/21.
 */
public class TestUtils {

    public static final String TEST_CATEGORY_UUID = UUID.randomUUID().toString();
    public static final String TEST_CATEGORY_NAME = "Test Category";
    public static final String TEST_CATEGORY_NAME_UPDATED = "Test Category Updated";

    public static final String TEST_ATTR_1_UUID = UUID.randomUUID().toString();
    public static final String TEST_ATTR_NAME_1 = "Attribute Name 1";

    public static final String TEST_ATTR_2_UUID = UUID.randomUUID().toString();
    public static final String TEST_ATTR_NAME_2 = "Attribute Name 2";

    public static final String TEST_ITEM_UUID = UUID.randomUUID().toString();
    public static final String TEST_ITEM_NAME = "Test Item";

    public static Category createMockCategory(int offset) {
        Category category = new Category();
        category.setUuid(appendOffset(offset, TEST_CATEGORY_UUID));
        category.setName(appendOffset(offset, TEST_CATEGORY_NAME));
        category.setCreated(LocalDateTime.now());

        return category;
    }

    public static Item createMockItem(int offset) {
        Item item = new Item();
        item.setUuid(appendOffset(offset, TEST_ITEM_UUID));
        item.setName(appendOffset(offset, TEST_ITEM_NAME));
        item.setCreated(LocalDateTime.now());
        item.setItemType(ItemType.SAMPLE);

        return item;
    }

    public static List<Attribute> createMockAttribute(int offset) {
        return List.of(
                new Attribute(appendOffset(offset, TEST_ATTR_1_UUID), appendOffset(offset, TEST_ATTR_NAME_1)),
                new Attribute(appendOffset(offset, TEST_ATTR_2_UUID), appendOffset(offset, TEST_ATTR_NAME_2))
        );
    }

    public static String appendOffset(int offset, String str) {
        return offset + "_" + str;
    }
}
