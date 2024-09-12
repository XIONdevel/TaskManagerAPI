package org.noix.api.manager.shopping.lists.dto;

import org.noix.api.manager.shopping.items.dto.ShoppingItemDTO;
import org.noix.api.manager.shopping.lists.ShoppingList;
import org.noix.api.manager.users.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListMapper {

    public ShoppingListDTO toDTO(ShoppingList list) {
        return ShoppingListDTO.builder()
                .id(list.getId())
                .name(list.getName())
                .build();
    }

    public ShoppingListDTO toDTO(ShoppingList list, List<ShoppingItemDTO> items) {
        return ShoppingListDTO.builder()
                .id(list.getId())
                .name(list.getName())
                .items(items)
                .build();
    }

    public ShoppingList toDomain(ShoppingListDTO dto, User owner) {
        return ShoppingList.builder()
                .id(dto.getId())
                .name(dto.getName())
                .owner(owner)
                .build();
    }
}
