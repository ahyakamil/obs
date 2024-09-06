package com.obs.app.obs_api.service;

import com.obs.app.obs_api.dto.Object;
import com.obs.app.obs_api.dto.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {
    void create(Object object);
    void deleteById(Long id);
    Page<ItemDto> getAll(Pageable pageable);
    ItemDto getById(Long id);
    void updateById(Long id, Object itemDto);
}
