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
public class CreateListRequest {

    private String name;
    private List<ShoppingItem> items;


    public boolean isNameValid() {
        if (name == null) return false;
        return name.length() > 3 && name.length() < 30;
    }

}
