package org.noix.api.manager.shopping.items.dto;

import org.noix.api.manager.shopping.items.ShoppingItem;
import org.noix.api.manager.shopping.lists.ShoppingList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemMapper {

    public List<ShoppingItemDTO> toDTO(List<ShoppingItem> items) {
        List<ShoppingItemDTO> result = new ArrayList<>();
        for (ShoppingItem item : items) {
            result.add(toDTO(item));
        }
        return result;
    }

    public ShoppingItemDTO toDTO(ShoppingItem item) {
        return ShoppingItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .bought(item.getBought())
                .listId(item.getListId())
                .build();
    }

    public ShoppingItemDTO toDTO(ShoppingItem item, ShoppingList list) {
        return ShoppingItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .bought(item.getBought())
                .listId(list.getId())
                .build();
    }

    public ShoppingItem toDomain(ShoppingItemDTO item, ShoppingList list) {
        return ShoppingItem.builder()
                .id(item.getId())
                .name(item.getName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .bought(item.getBought())
                .list(list)
                .build();
    }
}
