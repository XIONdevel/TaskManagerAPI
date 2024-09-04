package org.noix.api.manager.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.noix.api.manager.shopping.items.ShoppingItem;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingListDTO {

    private Long id;
    private String name;
    private List<ShoppingItem> items;

}
