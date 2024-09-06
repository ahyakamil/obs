package com.obs.app.obs_api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    private Integer qty;
    
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}
