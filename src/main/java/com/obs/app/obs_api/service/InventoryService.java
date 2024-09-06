package com.obs.app.obs_api.service;

import com.obs.app.obs_api.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryService {
    void create(CreateInventoryDto createInventoryDto);
    void deleteById(Long id);
    Page<InventoryDto> getAll(Pageable pageable);
    InventoryDto getById(Long id);
    void updateById(Long id, UpdateInventoryDto createInventoryDto);
}
