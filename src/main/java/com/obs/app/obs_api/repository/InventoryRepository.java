package com.obs.app.obs_api.repository;

import com.obs.app.obs_api.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository  extends JpaRepository<Inventory, Long> {
}
