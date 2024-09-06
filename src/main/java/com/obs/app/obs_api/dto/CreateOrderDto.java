package com.obs.app.obs_api.dto;

import com.obs.app.obs_api.constant.ErrorMessage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderDto {
    @NotNull(message = ErrorMessage.ORDER_ITEM_ID_MUST_NOT_BE_NULL)
    Long itemId;

    @NotNull(message = ErrorMessage.ORDER_QTY_MUST_NOT_BE_NULL)
    @Min(value = 1, message = ErrorMessage.ORDER_QTY_MUST_GREATER_THAN_ZERO)
    Integer qty;

}
