package com.lforward.challange.service;

import com.lforward.challange.exception.RecordNotFoundException;
import com.lforward.challange.model.dto.ItemRequest;
import com.lforward.challange.model.entity.Category;
import com.lforward.challange.model.entity.Item;
import com.lforward.challange.repository.ItemRepository;
import com.lforward.challange.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lforward.challange.utils.Utils.ITEM_PER_PAGE;
import static com.lforward.challange.utils.Utils.generateUuid;

/**
 * @author bashir
 * @since 20/10/21.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final CategoryService categoryService;

    @Transactional
    public Item save(ItemRequest request) {
        Item category = createItem(request);
        return itemRepository.save(category);
    }

    @Transactional
    public Item update(ItemRequest request) {
        return itemRepository.findItemByUuid(request.uuid())
                .map(category -> getMutatedUpdatedItem(request, category))
                .orElseThrow(() -> new RecordNotFoundException("Item"));
    }

    @Transactional
    public List<Item> delete(List<String> uuidList) {
        return Optional.ofNullable(uuidList)
                .map(itemRepository::findItemByUuidIn)
                .map(itemList -> {
                    itemRepository.deleteAll(itemList);
                    log.debug("[ItemService:delete]: Deleted uuidList: {}", String.join(",", uuidList));
                    return itemList;
                }).stream()
                .flatMap(List::stream)
                .peek(itm -> Hibernate.initialize(itm.getCategoryList()))
                .collect(Collectors.toList());
    }

    public List<Item> findByUuidList(List<String> uuidList) {
        return Optional.ofNullable(uuidList)
                .map(itemRepository::findItemByUuidIn)
                .orElseGet(Collections::emptyList);
    }

    public Page<Item> listAll(int pageOffset) {
        Pageable pageable = PageRequest.of(pageOffset, ITEM_PER_PAGE, Sort.by("name"));
        return itemRepository.findAll(pageable);
    }

    public Page<Item> findByCategoryUuid(String categoryUuid, int pageOffset) {
        Pageable pageable = PageRequest.of(pageOffset, ITEM_PER_PAGE, Sort.by("name"));
        return itemRepository.findItemsByCategoryListUuid(categoryUuid, pageable);
    }

    public Item createItem(ItemRequest request) {
        Item item = new Item();

        item.setUuid(generateUuid());
        item.setName(request.name());
        item.setItemType(request.itemType());
        item.setCreated(LocalDateTime.now());

        Optional.ofNullable(request.categoryUuids())
                .map(categoryService::findByUuidList)
                .ifPresent(item::setCategoryList);

        return item;
    }

    public Item getMutatedUpdatedItem(ItemRequest request, Item item) {
        item.setUpdated(LocalDateTime.now());
        item.setName(request.name());
        item.setItemType(request.itemType());

        List<Category> requestCategories = categoryService.findByUuidList(request.categoryUuids());

        List<Category> addedCategories = Utils.nullSafeList(requestCategories).stream()
                .filter(cat -> !item.getCategoryList().contains(cat))
                .collect(Collectors.toList());

        List<Category> removedCategories = item.getCategoryList().stream()
                .filter(cat -> !requestCategories.contains(cat))
                .collect(Collectors.toList());

        item.getCategoryList().removeAll(removedCategories);
        item.getCategoryList().addAll(addedCategories);

        return item;
    }
}
