package com.obs.app.obs_api.dto;

import com.obs.app.obs_api.constant.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateItemDto {
    @NotBlank(message = ErrorMessage.ITEM_NAME_MUST_NOT_BE_BLANK)
    private String name;

    @NotNull(message = ErrorMessage.ITEM_PRICE_MUST_NOT_BE_NULL)
    private Integer price;
}
