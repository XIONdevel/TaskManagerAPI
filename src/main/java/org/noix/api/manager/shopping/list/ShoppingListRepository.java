package org.noix.api.manager.shopping.list;

import org.noix.api.manager.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {


    List<ShoppingList> findAllByOwner(User owner);

    Optional<ShoppingList> findByIdAndOwner(Long id, User owner);

    void deleteAllByOwner(User owner);
}