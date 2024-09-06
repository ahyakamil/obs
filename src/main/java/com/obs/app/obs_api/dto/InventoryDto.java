package com.obs.app.obs_api.dto;

import com.obs.app.obs_api.constant.InventoryTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryDto {
    Long id;
    Integer qty;
    String type;
    ItemDto item;
}
