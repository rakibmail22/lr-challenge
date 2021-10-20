package com.lforward.challenge.web.controller;

import com.lforward.challenge.model.dto.CategoryRequest;
import com.lforward.challenge.model.entity.Category;
import com.lforward.challenge.model.validation.UpdateValidationGroup;
import com.lforward.challenge.service.CategoryService;
import com.lforward.challenge.web.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static com.lforward.challenge.web.utils.WebUtils.getLocation;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author bashir
 * @since 19/10/21.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = ("/v1/category/"), consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class CategoryController {

    private final CategoryService categoryService;

    private final RequestValidator requestValidator;

    @PostMapping(value = "/save")
    public ResponseEntity<Category> save(@RequestBody @Valid CategoryRequest request) {
        log.debug("[CategoryController:create]: {}", request);

        Category category = categoryService.save(request);

        return ResponseEntity.created(getLocation(category.getUuid())).body(category);
    }

    @PutMapping(value = "/update")
    public Category update(@RequestBody @Validated(UpdateValidationGroup.class) CategoryRequest request) {
        log.debug("[CategoryController:update]: {}", request);

        return categoryService.update(request);
    }

    @DeleteMapping(value = "/delete")
    public List<Category> delete(@RequestBody List<String> uuidList) {
        log.debug("[CategoryController:delete]: {}", String.join(",", uuidList));

        requestValidator.validateListSize(uuidList);

        return categoryService.delete(uuidList);
    }

    @GetMapping(value = "/uuid/{uuid}")
    public Category get(@NotBlank String uuid) {
        log.debug("[CategoryController:get]: {}", uuid);

        return categoryService.get(uuid);
    }

    @GetMapping(value = "/findByUuid/{uuidList}")
    public List<Category> findByUuidList(@PathVariable List<String> uuidList) {
        log.debug("[CategoryController:listByUuid]: {}", uuidList);

        requestValidator.validateListSize(uuidList);

        return categoryService.findByUuidList(uuidList);
    }

    @GetMapping(value = "/list/{pageIndex}")
    public Page<Category> listAll(@PathVariable(required = false) int pageIndex) {
        log.debug("[CategoryController:list]: {}", pageIndex);

        return categoryService.listAll(pageIndex);
    }
}
