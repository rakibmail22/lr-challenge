package com.lforward.challange.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lforward.challange.model.dto.ItemRequest;
import com.lforward.challange.model.entity.Attribute;
import com.lforward.challange.model.entity.Category;
import com.lforward.challange.model.entity.Item;
import com.lforward.challange.model.entity.ItemType;

import java.time.LocalDateTime;
import java.util.Collections;
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

    public static final String TST_CAT_UUID_1 = "TST-CAT-UUID-1";
    public static final String TST_CAT_UUID_2 = "TST-CAT-UUID-2";

    public static final String TST_ITM_UUID_1 = "TST-ITM-UUID-1";
    public static final String TST_ITM_NAME_1 = "TST-ITM-NAME-1";
    public static final String TST_ITM_SAVE_1_NAME_UPDATED = "TST-ITM-NAME-1-UPDATED";

    public static final String TST_ITM_UUID_2 = "TST-ITM-UUID-2";
    public static final String TST_ITM_NAME_2 = "TST-ITM-NAME-2";

    public static final String TST_ITM_UUID_3 = "TST-ITM-UUID-3";
    public static final String TST_ITM_NAME_3 = "TST-ITM-NAME-3";

    public static final String TST_ITM_SAVE_NAME_1 = "TST-ITM-SAVE-NAME-1";
    public static final String TST_ITM_SAVE_UUID_1 = "TST-ITM-SAVE-UUID-1";

    public static final int BASE_ITEM_COUNT = 3;
    public static final int BASE_CATEGORY_COUNT = 2;

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

    public static ItemRequest createMockItemRequest() {
        return new ItemRequest(TST_ITM_SAVE_UUID_1, TST_ITM_SAVE_NAME_1, ItemType.SAMPLE, Collections.emptyList());
    }

    public static ItemRequest createMockItemUpdateRequest() {
        return new ItemRequest(
                TST_ITM_UUID_1, TST_ITM_SAVE_1_NAME_UPDATED,
                ItemType.CHEMICAL, List.of(TST_CAT_UUID_1, TST_CAT_UUID_2)
        );
    }

    public static <T> String getJsonString(T t) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(t);
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
