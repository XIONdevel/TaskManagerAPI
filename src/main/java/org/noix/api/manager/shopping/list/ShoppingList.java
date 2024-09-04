package org.noix.api.manager.shopping.list;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.noix.api.manager.shopping.dto.ShoppingListDTO;
import org.noix.api.manager.shopping.items.ShoppingItem;
import org.noix.api.manager.users.User;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shopping_list")
public class ShoppingList {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    private User owner;

    @OneToMany(mappedBy = "list", cascade = CascadeType.REMOVE)
    private List<ShoppingItem> items;


    public ShoppingListDTO toDTO() {
        return ShoppingListDTO.builder()
                .id(id)
                .name(name)
                .items(items)
                .build();
    }

    public Long getOwnerId() {
        return owner.getId();
    }

    public boolean isNameValid() {
        if (name == null) return false;
        return name.length() > 3 && name.length() < 30;
    }

}

