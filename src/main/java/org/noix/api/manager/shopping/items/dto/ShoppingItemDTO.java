package org.noix.api.manager.shopping.items.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingItemDTO {

    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private Boolean bought;
    private Long listId;

}
