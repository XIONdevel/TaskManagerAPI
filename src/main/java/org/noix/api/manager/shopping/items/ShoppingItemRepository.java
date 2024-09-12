package org.noix.api.manager.shopping.items;

import jakarta.transaction.Transactional;
import org.noix.api.manager.shopping.items.dto.ShoppingItemDTO;
import org.noix.api.manager.shopping.lists.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {

    boolean existsByIdAndList(long itemId, ShoppingList list);

    List<ShoppingItem> findAllByList(ShoppingList list);

    @Modifying
    @Transactional
    void deleteAllByList(ShoppingList list);
}
