package org.noix.api.manager.shopping.lists;

import org.noix.api.manager.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {

    List<ShoppingList> findAllByOwner(User owner);

    boolean existsByIdAndOwnerNot(Long listId, User user);

}
