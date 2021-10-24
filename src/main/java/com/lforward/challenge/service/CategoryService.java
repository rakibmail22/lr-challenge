package com.lforward.challenge.service;

import com.lforward.challenge.exception.RecordNotFoundException;
import com.lforward.challenge.model.dto.CategoryRequest;
import com.lforward.challenge.model.entity.Attribute;
import com.lforward.challenge.model.entity.Category;
import com.lforward.challenge.repository.CategoryRepository;
import com.lforward.challenge.utils.Utils;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lforward.challenge.utils.Utils.ITEM_PER_PAGE;
import static com.lforward.challenge.utils.Utils.generateUuid;

/**
 * @author bashir
 * @since 19/10/21.¬ø
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category save(CategoryRequest request) {
        Category category = createCategory(request);
        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(CategoryRequest request) {
        return categoryRepository.findCategoryByUuid(request.uuid())
                .map(category -> getMutatedUpdatedCategory(request, category))
                .orElseThrow(() -> new RecordNotFoundException("Category"));
    }

    @Transactional
    public List<Category> delete(List<String> uuidList) {
        return Optional.ofNullable(uuidList)
                .map(categoryRepository::findCategoryByUuidIn)
                .map(categories -> {
                    categoryRepository.deleteAll(categories);
                    log.debug("[CategoryService:delete]: Deleted uuidList: {}", String.join(",", uuidList));
                    return categories;
                }).stream()
                .flatMap(List::stream)
                .peek(category -> Hibernate.initialize(category.getAttributeList()))
                .collect(Collectors.toList());
    }

    public List<Category> findByUuidList(List<String> uuidList) {
        return categoryRepository.findCategoryByUuidIn(Utils.nullSafeList(uuidList));
    }

    public Category get(String uuid) {
        return categoryRepository.findCategoryByUuid(uuid).orElseThrow(() -> new RecordNotFoundException("Category"));
    }

    public Page<Category> listAll(int startOffset) {
        Pageable pageable = PageRequest.of(startOffset, ITEM_PER_PAGE, Sort.by("name"));
        return categoryRepository.findAll(pageable);
    }

    public Category createCategory(CategoryRequest request) {
        Category category = new Category();

        category.setUuid(generateUuid());
        category.setName(request.name());
        category.setCreated(LocalDateTime.now());

        Utils.nullSafeList(request.attributes()).stream()
                .map(attr -> new Attribute(generateUuid(), attr))
                .forEach(category.getAttributeList()::add);

        return category;
    }

    public Category getMutatedUpdatedCategory(CategoryRequest request, Category category) {
        category.setUpdated(LocalDateTime.now());
        category.setName(request.name());

        List<String> existingAttributes = category.getAttributeList().stream()
                .map(Attribute::getValue)
                .collect(Collectors.toList());

        List<Attribute> newAttributes = Utils.nullSafeList(request.attributes()).stream()
                .filter(attr -> !existingAttributes.contains(attr))
                .map(attr -> new Attribute(generateUuid(), attr))
                .collect(Collectors.toList());

        List<Attribute> deletedAttributes = category.getAttributeList().stream()
                .filter(a -> !request.attributes().contains(a.getValue()))
                .collect(Collectors.toList());

        category.getAttributeList().removeAll(deletedAttributes);
        category.getAttributeList().addAll(newAttributes);

        return category;
    }
}
