package com.obs.app.obs_api.controller;

import com.obs.app.obs_api.common.ResponseWrapper;
import com.obs.app.obs_api.dto.*;
import com.obs.app.obs_api.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventories")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<Void>> create(@RequestBody CreateInventoryDto createInventoryDto) {
        inventoryService.create(createInventoryDto);
        return new ResponseWrapper<Void>().buildResponseCreated();
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<InventoryDto>>> getAll(Pageable pageable) {
        Page<InventoryDto> inventories = inventoryService.getAll(pageable);
        return new ResponseWrapper<Page<InventoryDto>>().buildResponseOk(inventories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<InventoryDto>> getById(@PathVariable("id") Long id) {
        InventoryDto item = inventoryService.getById(id);
        return new ResponseWrapper<InventoryDto>().buildResponseOk(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> updateById(@PathVariable("id") Long id, @RequestBody UpdateInventoryDto inventoryDto) {
        inventoryService.updateById(id, inventoryDto);
        return new ResponseWrapper<Void>().buildResponseOk();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteById(@PathVariable("id") Long id) {
        inventoryService.deleteById(id);
        return new ResponseWrapper<Void>().buildResponseOk();
    }

}
