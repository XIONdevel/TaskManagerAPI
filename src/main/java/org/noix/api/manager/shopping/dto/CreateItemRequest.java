package org.noix.api.manager.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemRequest {

    private String name;
    private Double price;
    private Integer quantity;
    private Boolean bought;

}
