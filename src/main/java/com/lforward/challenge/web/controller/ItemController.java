package com.lforward.challenge.web.controller;

import com.lforward.challenge.model.dto.ItemRequest;
import com.lforward.challenge.model.entity.Item;
import com.lforward.challenge.model.validation.UpdateValidationGroup;
import com.lforward.challenge.service.ItemService;
import com.lforward.challenge.web.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

import static com.lforward.challenge.web.utils.WebUtils.getLocation;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

/**
 * @author bashir
 * @since 20/10/21.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = ("/v1/item/"), produces = APPLICATION_JSON_VALUE)
public class ItemController {

    private final ItemService itemService;

    private final RequestValidator requestValidator;

    @PostMapping(value = "/save", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> save(@RequestBody @Valid ItemRequest request) {
        log.debug("[ItemController:save]: {}", request);

        Item item = itemService.save(request);

        return ResponseEntity.created(getLocation(item.getUuid())).body(item);
    }

    @PutMapping(value = "/update", consumes = APPLICATION_JSON_VALUE)
    public Item update(@RequestBody @Validated(UpdateValidationGroup.class) ItemRequest request) {
        log.debug("[ItemController:update]: {}", request);

        return itemService.update(request);
    }

    @DeleteMapping(value = "/delete", consumes = APPLICATION_JSON_VALUE)
    public List<Item> delete(@RequestBody List<String> uuidList) {
        log.debug("[ItemController:delete]: {}", String.join(",", uuidList));

        requestValidator.validateListSize(uuidList);

        return itemService.delete(uuidList);
    }

    @GetMapping(value = "/findByUuidList/{uuidList}")
    public List<Item> findByUuidList(@PathVariable List<String> uuidList) {
        log.debug("[ItemController:listByUuid]: {}", uuidList);

        requestValidator.validateListSize(uuidList);

        return itemService.findByUuidList(uuidList);
    }

    @GetMapping(value = "/list/{pageIndex}")
    public Page<Item> listAll(@PathVariable(required = false) int pageIndex) {
        log.debug("[ItemController:listAll]: {}", pageIndex);

        return itemService.listAll(pageIndex);
    }

    @GetMapping(value = "/listByCategory/{categoryUuid}/{pageIndex}")
    public Page<Item> listByCategory(@PathVariable @NotEmpty String categoryUuid,
                                     @PathVariable(required = false) int pageIndex) {
        log.debug("[ItemController:listByCategory]: pageIndex {}, categoryUuid {}", pageIndex, categoryUuid);

        return itemService.findByCategoryUuid(categoryUuid, pageIndex);
    }
}