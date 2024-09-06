package com.obs.app.obs_api.mapper;

import com.obs.app.obs_api.domain.Item;
import com.obs.app.obs_api.dto.ItemDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper {
    public ItemDto toDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setPrice(item.getPrice());
        dto.setStock(item.getStock());
        return dto;
    }

    public List<ItemDto> toDtoList(List<Item> items) {
        return items.stream().map(this::toDto)
                .collect(Collectors.toList());

    }
}
