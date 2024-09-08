package com.obs.app.obs_api.unitTest;

import com.obs.app.obs_api.constant.ErrorMessage;
import com.obs.app.obs_api.domain.Item;
import com.obs.app.obs_api.dto.CreateItemDto;
import com.obs.app.obs_api.dto.ItemDto;
import com.obs.app.obs_api.exception.BadRequestException;
import com.obs.app.obs_api.repository.ItemRepository;
import com.obs.app.obs_api.service.ItemService;
import com.obs.app.obs_api.service.impl.ItemServiceImpl;
import com.obs.app.obs_api.validation.Validation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ItemTest {
    @Spy
    Validation validation;

    @Mock
    ItemRepository itemRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    public void given_valid_item_data_when_create_then_success() {
        CreateItemDto createItemDto = new CreateItemDto();
        createItemDto.setName("Pencil");
        createItemDto.setPrice(5);

        itemService.create(createItemDto);

        Mockito.verify(itemRepository, Mockito.times(1)).save(Mockito.any(Item.class));
    }

    @Test
    public void given_invalid_item_data_price_zero_when_create_then_error() {
        CreateItemDto createItemDto = new CreateItemDto();
        createItemDto.setName("Pencil");
        createItemDto.setPrice(0);

        assertThrows(BadRequestException.class, () -> itemService.create(createItemDto));
        try {
            itemService.create(createItemDto);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(ErrorMessage.ITEM_PRICE_MUST_GREATER_THAN_ZERO));
        }
    }

    @Test
    public void given_invalid_item_data_price_null_when_create_then_error() {
        CreateItemDto createItemDto = new CreateItemDto();
        createItemDto.setName("Pencil");
        createItemDto.setPrice(null);

        assertThrows(BadRequestException.class, () -> itemService.create(createItemDto));
        try {
            itemService.create(createItemDto);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(ErrorMessage.ITEM_PRICE_MUST_NOT_BE_NULL));
        }
    }

    @Test
    public void given_invalid_item_data_name_is_blank_when_create_then_error() {
        CreateItemDto createItemDto = new CreateItemDto();
        createItemDto.setName(null);
        createItemDto.setPrice(5);

        assertThrows(BadRequestException.class, () -> itemService.create(createItemDto));
        try {
            itemService.create(createItemDto);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(ErrorMessage.ITEM_NAME_MUST_NOT_BE_BLANK));
        }

        createItemDto.setName("");
        assertThrows(BadRequestException.class, () -> itemService.create(createItemDto));
        try {
            itemService.create(createItemDto);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(ErrorMessage.ITEM_NAME_MUST_NOT_BE_BLANK));
        }

        createItemDto.setName("      ");
        assertThrows(BadRequestException.class, () -> itemService.create(createItemDto));
        try {
            itemService.create(createItemDto);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(ErrorMessage.ITEM_NAME_MUST_NOT_BE_BLANK));
        }
    }
}
