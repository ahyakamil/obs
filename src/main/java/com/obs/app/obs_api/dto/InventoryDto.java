package com.obs.app.obs_api.dto;

import com.obs.app.obs_api.constant.InventoryTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryDto {
    private Long id;
    private Integer qty;
    private String type;
    private ItemDto item;
}
