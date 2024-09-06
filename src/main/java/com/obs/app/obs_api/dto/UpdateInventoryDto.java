package com.obs.app.obs_api.dto;

import com.obs.app.obs_api.constant.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateInventoryDto {
    @NotNull(message = ErrorMessage.INVENTORY_QTY_MUST_NOT_BE_NULL)
    Integer qty;

    @NotBlank(message = ErrorMessage.INVENTORY_TYPE_MUST_NOT_BE_BLANK)
    String type;

}
