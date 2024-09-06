package com.obs.app.obs_api.controller;

import com.obs.app.obs_api.common.ResponseWrapper;
import com.obs.app.obs_api.dto.*;
import com.obs.app.obs_api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<Void>> create(@RequestBody CreateOrderDto createOrderDto) {
        orderService.create(createOrderDto);
        return new ResponseWrapper<Void>().buildResponseCreated();
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<OrderDto>>> getAll(Pageable pageable) {
        Page<OrderDto> orders = orderService.getAll(pageable);
        return new ResponseWrapper<Page<OrderDto>>().buildResponseOk(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<OrderDto>> getById(@PathVariable("id") Long id) {
        OrderDto order = orderService.getById(id);
        return new ResponseWrapper<OrderDto>().buildResponseOk(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> updateById(@PathVariable("id") Long id, @RequestBody UpdateOrderDto orderDto) {
        orderService.updateById(id, orderDto);
        return new ResponseWrapper<Void>().buildResponseOk();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteById(@PathVariable("id") Long id) {
        orderService.deleteById(id);
        return new ResponseWrapper<Void>().buildResponseOk();
    }

}
