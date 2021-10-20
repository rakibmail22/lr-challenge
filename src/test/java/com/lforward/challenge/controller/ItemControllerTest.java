package com.lforward.challenge.controller;

import com.lforward.challenge.model.entity.Item;
import com.lforward.challenge.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.lforward.challenge.utils.TestUtils.*;
import static java.lang.String.format;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author bashir
 * @since 20/10/21.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {

    private static final String ITEM_API_URL = "/v1/item/%s";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void findByUuidAndCheckResponse() throws Exception {
        mockMvc.perform(get(format(ITEM_API_URL, "findByUuidList/") + join(",", TST_ITM_UUID_1, TST_ITM_UUID_2))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(TST_ITM_NAME_1)))
                .andExpect(jsonPath("$[1].name", is(TST_ITM_NAME_2)));
    }

    @Test
    public void listByCategoryAndCheckResponse() throws Exception {
        mockMvc.perform(get(format(ITEM_API_URL, "listByCategory/") + join("/", TST_CAT_UUID_1, "0"))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()", is(2)));
    }

    @Test
    public void saveItemAndCheckResponse() throws Exception {
        mockMvc.perform(post(format(ITEM_API_URL, "save"))
                        .content(getJsonString(createMockItemRequest()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(TST_ITM_SAVE_NAME_1)));
    }

    @Test
    public void updateItemAndCheckResponse() throws Exception {
        mockMvc.perform(put(format(ITEM_API_URL, "update"))
                        .content(getJsonString(createMockItemUpdateRequest()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(TST_ITM_SAVE_1_NAME_UPDATED)));

        itemRepository.findItemByUuid(TST_ITM_UUID_1)
                .map(Item::getName)
                .ifPresentOrElse(
                        n -> assertThat(n).isEqualTo(TST_ITM_SAVE_1_NAME_UPDATED),
                        () -> {
                            throw new IllegalStateException();
                        }
                );
    }

    @Test
    public void deleteAndCheckResponse() throws Exception {
        Item item = itemRepository.findItemByUuid(TST_ITM_UUID_3).orElse(null);
        assertThat(item).extracting(Item::getName).isEqualTo(TST_ITM_NAME_3);

        mockMvc.perform(delete(format(ITEM_API_URL, "delete"))
                        .content(getJsonString(List.of(TST_ITM_UUID_3)))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(TST_ITM_NAME_3)));

        item = itemRepository.findItemByUuid(TST_ITM_UUID_3).orElse(null);
        assertThat(item).isNull();
    }
}
