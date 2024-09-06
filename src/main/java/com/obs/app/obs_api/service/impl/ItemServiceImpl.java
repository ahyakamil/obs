package com.obs.app.obs_api.service.impl;

import com.obs.app.obs_api.domain.Item;
import com.obs.app.obs_api.dto.CreateItemDto;
import com.obs.app.obs_api.dto.ItemDto;
import com.obs.app.obs_api.exception.ConflictException;
import com.obs.app.obs_api.exception.NotFoundException;
import com.obs.app.obs_api.mapper.ItemMapper;
import com.obs.app.obs_api.repository.ItemRepository;
import com.obs.app.obs_api.service.ItemService;
import com.obs.app.obs_api.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    Validation validation;

    @Override
    public void create(CreateItemDto createItemDto) {
        try {
            validation.validate(createItemDto);
            Item item = new Item();
            item.setName(createItemDto.getName());
            item.setPrice(createItemDto.getPrice());
            itemRepository.save(item);
        } catch(DataIntegrityViolationException e) {
            throw new ConflictException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if(item.isPresent()) {
            itemRepository.deleteById(id);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public Page<ItemDto> getAll(Pageable pageable) {
        Page<Item> items =  itemRepository.findAll(pageable);
        return items.map(item -> itemMapper.toDto(item));
    }

    @Override
    public ItemDto getById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if(item.isPresent()) {
            return itemMapper.toDto(item.get());
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public void updateById(Long id, CreateItemDto itemDto) {
        Optional<Item> item = itemRepository.findById(id);
        if(item.isPresent()) {
            validation.validate(itemDto);
            Item data = item.get();
            data.setPrice(itemDto.getPrice());
            data.setName(itemDto.getName());
            itemRepository.save(data);
        } else {
            throw new NotFoundException();
        }
    }
}
