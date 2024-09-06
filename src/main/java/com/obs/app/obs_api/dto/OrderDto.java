package com.obs.app.obs_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private Integer qty;
    private Integer price;
    private ItemDto item;
}
