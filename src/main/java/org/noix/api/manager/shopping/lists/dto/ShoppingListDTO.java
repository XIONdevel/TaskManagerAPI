package org.noix.api.manager.shopping.lists.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.noix.api.manager.shopping.items.dto.ShoppingItemDTO;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingListDTO {

    private Long id;
    private String name;
    private List<ShoppingItemDTO> items;

}
