package com.obs.app.obs_api.mapper;

import com.obs.app.obs_api.domain.OrderItem;
import com.obs.app.obs_api.dto.OrderDto;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    ItemMapper itemMapper = new ItemMapper();

    public OrderDto toDto(OrderItem order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setQty(order.getQty());
        dto.setPrice(order.getPrice());
        dto.setItem(itemMapper.toDto(order.getItem()));
        return dto;
    }
}
