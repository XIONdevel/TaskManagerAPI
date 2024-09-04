package org.noix.api.manager.shopping.items;

import org.noix.api.manager.shopping.list.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {


    void deleteAllByList(ShoppingList list);
}
