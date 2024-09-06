package com.obs.app.obs_api.repository;

import com.obs.app.obs_api.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(value = "SELECT * FROM item WHERE id = :id FOR UPDATE", nativeQuery = true)
    Item findByIdForUpdate(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE item SET stock = :stock WHERE id = :id", nativeQuery = true)
    void updateStockById(Long id, Integer stock);
}
