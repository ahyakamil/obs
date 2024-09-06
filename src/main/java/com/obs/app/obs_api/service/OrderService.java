package com.obs.app.obs_api.service;

import com.obs.app.obs_api.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    void create(CreateOrderDto createOrderDto);
    void deleteById(Long id);
    Page<OrderDto> getAll(Pageable pageable);
    OrderDto getById(Long id);
    void updateById(Long id, UpdateOrderDto orderDto);
}
