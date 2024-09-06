package com.obs.app.obs_api.service.impl;

import com.obs.app.obs_api.constant.ErrorMessage;
import com.obs.app.obs_api.constant.InventoryTypeEnum;
import com.obs.app.obs_api.domain.Inventory;
import com.obs.app.obs_api.domain.Item;
import com.obs.app.obs_api.dto.CreateInventoryDto;
import com.obs.app.obs_api.dto.InventoryDto;
import com.obs.app.obs_api.dto.UpdateInventoryDto;
import com.obs.app.obs_api.exception.BadRequestException;
import com.obs.app.obs_api.exception.NotFoundException;
import com.obs.app.obs_api.mapper.InventoryMapper;
import com.obs.app.obs_api.repository.InventoryRepository;
import com.obs.app.obs_api.repository.ItemRepository;
import com.obs.app.obs_api.service.InventoryService;
import com.obs.app.obs_api.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    Validation validation;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    InventoryMapper inventoryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(CreateInventoryDto createInventoryDto) {
        validation.validate(createInventoryDto);
        Optional<Item> item = itemRepository.findById(createInventoryDto.getItemId());
        if(item.isPresent()) {
            InventoryTypeEnum type = InventoryTypeEnum.fromValue(createInventoryDto.getType());
            Inventory inventory = new Inventory();
            inventory.setQty(createInventoryDto.getQty());
            inventory.setType(type);
            inventory.setItem(item.get());
            createInventoryAndUpdateStock(inventory);
        } else {
            throw new NotFoundException();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    private void createInventoryAndUpdateStock(Inventory inventory) {
        Item item = itemRepository.findByIdForUpdate(inventory.getItem().getId());
        inventoryRepository.save(inventory);
        if(inventory.getType() == InventoryTypeEnum.TOP_UP) {
            Integer stock = item.getStock() + inventory.getQty();
            itemRepository.updateStockById(item.getId(), stock);
        } else if(inventory.getType() == InventoryTypeEnum.WITHDRAWAL) {
            Integer stock = item.getStock() - inventory.getQty();
            if(stock >= 0) {
                itemRepository.updateStockById(item.getId(), stock);
            } else {
                throw new BadRequestException(ErrorMessage.STOCK_CANNOT_BE_NEGATIVE);
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        if(inventory.isPresent()) {
            inventoryRepository.deleteById(id);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public Page<InventoryDto> getAll(Pageable pageable) {
        Page<Inventory> inventories =  inventoryRepository.findAll(pageable);
        return inventories.map(inventory -> inventoryMapper.toDto(inventory));
    }

    @Override
    public InventoryDto getById(Long id) {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        if(inventory.isPresent()) {
            return inventoryMapper.toDto(inventory.get());
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(Long id, UpdateInventoryDto inventoryDto) {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        if(inventory.isPresent()) {
            validation.validate(inventoryDto);
            InventoryTypeEnum type = InventoryTypeEnum.fromValue(inventoryDto.getType());
            Inventory data = inventory.get();

            Item item = itemRepository.findByIdForUpdate(data.getItem().getId());

            Integer stock = 0;
            //revert and update stock
            if(InventoryTypeEnum.fromValue(inventoryDto.getType()) == InventoryTypeEnum.TOP_UP) {
                if(data.getType() == InventoryTypeEnum.TOP_UP) {
                    stock = (item.getStock() - data.getQty()) + inventoryDto.getQty();
                } else if(data.getType() == InventoryTypeEnum.WITHDRAWAL) {
                    stock = (item.getStock() + data.getQty()) + inventoryDto.getQty();
                }
            } else if(InventoryTypeEnum.fromValue(inventoryDto.getType()) == InventoryTypeEnum.WITHDRAWAL) {
                if(data.getType() == InventoryTypeEnum.TOP_UP) {
                    stock = (item.getStock() - data.getQty()) - inventoryDto.getQty();
                } else if(data.getType() == InventoryTypeEnum.WITHDRAWAL) {
                    stock = (item.getStock() + data.getQty()) - inventoryDto.getQty();
                }
            }

            if(stock >= 0) {
                //update state
                data.setQty(inventoryDto.getQty());
                data.setType(type);
                inventoryRepository.save(data);
                itemRepository.updateStockById(item.getId(), stock);
            } else {
                throw new BadRequestException(ErrorMessage.STOCK_CANNOT_BE_NEGATIVE);
            }
        } else {
            throw new NotFoundException();
        }
    }
}
