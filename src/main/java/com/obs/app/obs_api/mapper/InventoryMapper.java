package com.obs.app.obs_api.mapper;

import com.obs.app.obs_api.domain.Inventory;
import com.obs.app.obs_api.domain.Item;
import com.obs.app.obs_api.dto.InventoryDto;
import com.obs.app.obs_api.dto.ItemDto;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {
    ItemMapper itemMapper = new ItemMapper();

    public InventoryDto toDto(Inventory inventory) {
        InventoryDto dto = new InventoryDto();
        dto.setId(inventory.getId());
        dto.setQty(inventory.getQty());
        dto.setType(inventory.getType().getValue());
        dto.setItem(itemMapper.toDto(inventory.getItem()));
        return dto;
    }
}
