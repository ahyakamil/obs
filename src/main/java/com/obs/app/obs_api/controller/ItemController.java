package com.obs.app.obs_api.controller;

import com.obs.app.obs_api.common.ResponseWrapper;
import com.obs.app.obs_api.dto.CreateItemDto;
import com.obs.app.obs_api.dto.ItemDto;
import com.obs.app.obs_api.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    ItemService itemService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<Void>> create(@RequestBody CreateItemDto createItemDto) {
        itemService.create(createItemDto);
        return new ResponseWrapper<Void>().buildResponseCreated();
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<ItemDto>>> getAll(Pageable pageable) {
        Page<ItemDto> items = itemService.getAll(pageable);
        return new ResponseWrapper<Page<ItemDto>>().buildResponseOk(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ItemDto>> getById(@PathVariable("id") Long id) {
        ItemDto item = itemService.getById(id);
        return new ResponseWrapper<ItemDto>().buildResponseOk(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> updateById(@PathVariable("id") Long id, @RequestBody CreateItemDto itemDto) {
        itemService.updateById(id, itemDto);
        return new ResponseWrapper<Void>().buildResponseOk();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteById(@PathVariable("id") Long id) {
        itemService.deleteById(id);
        return new ResponseWrapper<Void>().buildResponseOk();
    }
}
