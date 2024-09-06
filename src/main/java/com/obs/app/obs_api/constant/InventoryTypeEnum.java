package com.obs.app.obs_api.constant;

import com.obs.app.obs_api.exception.BadRequestException;
import lombok.Getter;

@Getter
public enum InventoryTypeEnum {
    TOP_UP("T"),
    WITHDRAWAL("W");

    private final String value;

    InventoryTypeEnum(String value) {
        this.value = value;
    }
    public static InventoryTypeEnum fromValue(String value) {
        for(InventoryTypeEnum type: InventoryTypeEnum.values()) {
            if(type.value.equals(value)) {
                return type;
            }
        }
        throw new BadRequestException("Unknown inventory type: " + value);
    }
}
