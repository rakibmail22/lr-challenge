package com.lforward.challenge.controller;

import com.lforward.challenge.model.dto.CategoryRequest;
import com.lforward.challenge.model.entity.Category;
import com.lforward.challenge.model.validation.UpdateValidationGroup;
import com.lforward.challenge.service.CategoryService;
import com.lforward.challenge.validator.EntityValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author bashir
 * @since 19/10/21.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = ("/v1/category/"))
public class CategoryController {

    private final CategoryService categoryService;

    private final EntityValidator entityValidator;

    @PostMapping(value = "/save")
    public Category save(@RequestBody @Valid CategoryRequest request) {
        log.debug("[CategoryController:create]: {}", request);

        return categoryService.save(request);
    }

    @PutMapping(value = "/update")
    public Category update(@RequestBody @Validated(UpdateValidationGroup.class) CategoryRequest request) {
        log.debug("[CategoryController:update]: {}", request);

        return categoryService.update(request);
    }

    @DeleteMapping(value = "/delete")
    public List<Category> delete(@RequestBody List<String> uuidList) {
        log.debug("[CategoryController:delete]: {}", String.join(",", uuidList));

        entityValidator.validateListSize(uuidList);

        return categoryService.delete(uuidList);
    }

    @GetMapping(value = "/findByUuid/{uuidList}")
    public List<Category> findByUuidList(@PathVariable List<String> uuidList) {
        log.debug("[CategoryController:listByUuid]: {}", uuidList);

        entityValidator.validateListSize(uuidList);

        return categoryService.findByUuidList(uuidList);
    }

    @GetMapping(value = "/list/{pageIndex}")
    public Page<Category> listAll(@PathVariable(required = false) int pageIndex) {
        log.debug("[CategoryController:list]: {}", pageIndex);

        return categoryService.listAll(pageIndex);
    }
}
