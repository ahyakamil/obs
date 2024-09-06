package com.obs.app.obs_api.service.impl;

import com.obs.app.obs_api.constant.ErrorMessage;
import com.obs.app.obs_api.domain.Inventory;
import com.obs.app.obs_api.domain.Item;
import com.obs.app.obs_api.domain.OrderItem;
import com.obs.app.obs_api.dto.CreateOrderDto;
import com.obs.app.obs_api.dto.OrderDto;
import com.obs.app.obs_api.dto.UpdateOrderDto;
import com.obs.app.obs_api.exception.BadRequestException;
import com.obs.app.obs_api.exception.NotFoundException;
import com.obs.app.obs_api.exception.UnprocessableEntityException;
import com.obs.app.obs_api.mapper.OrderMapper;
import com.obs.app.obs_api.repository.ItemRepository;
import com.obs.app.obs_api.repository.OrderRepository;
import com.obs.app.obs_api.service.OrderService;
import com.obs.app.obs_api.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    Validation validation;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderMapper orderMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(CreateOrderDto createOrderDto) {
        validation.validate(createOrderDto);
        Optional<Item> item = itemRepository.findById(createOrderDto.getItemId());
        if(item.isPresent()) {
            OrderItem order = new OrderItem();
            order.setQty(createOrderDto.getQty());
            order.setPrice(item.get().getPrice());
            order.setItem(item.get());
            createOrder(order);
        } else {
            throw new NotFoundException();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void createOrder(OrderItem order) {
        Item item = itemRepository.findByIdForUpdate(order.getItem().getId());
        orderRepository.save(order);
        Integer stock = item.getStock() - order.getQty();
        if(stock >= 0) {
            itemRepository.updateStockById(item.getId(), stock);
        } else {
            throw new UnprocessableEntityException(ErrorMessage.ORDER_INSUFFICIENT_STOCK);
        }

    }

    @Override
    public void deleteById(Long id) {
        Optional<OrderItem> order = orderRepository.findById(id);
        if(order.isPresent()) {
            orderRepository.deleteById(id);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public Page<OrderDto> getAll(Pageable pageable) {
        Page<OrderItem> orders =  orderRepository.findAll(pageable);
        return orders.map(order -> orderMapper.toDto(order));
    }

    @Override
    public OrderDto getById(Long id) {
        Optional<OrderItem> order = orderRepository.findById(id);
        if(order.isPresent()) {
            return orderMapper.toDto(order.get());
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(Long id, UpdateOrderDto orderDto) {
        Optional<OrderItem> order = orderRepository.findById(id);
        if(order.isPresent()) {
            validation.validate(orderDto);
            Item item = itemRepository.findByIdForUpdate(order.get().getItem().getId());
            OrderItem data = order.get();

            //revert and update stock
            Integer stock = (item.getStock() + data.getQty()) - orderDto.getQty();
            if(stock >= 0) {
                data.setQty(orderDto.getQty());
                data.setPrice(item.getPrice());
                data.setItem(item);
                orderRepository.save(data);
                itemRepository.updateStockById(item.getId(), stock);
            } else {
                throw new UnprocessableEntityException(ErrorMessage.ORDER_INSUFFICIENT_STOCK);
            }
        } else {
            throw new NotFoundException();
        }
    }
}
