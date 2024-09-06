package com.obs.app.obs_api.service;

import com.obs.app.obs_api.dto.CreateItemDto;
import com.obs.app.obs_api.dto.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {
    void create(CreateItemDto createItemDto);
    void deleteById(Long id);
    Page<ItemDto> getAll(Pageable pageable);
    ItemDto getById(Long id);
    void updateById(Long id, CreateItemDto itemDto);
}
