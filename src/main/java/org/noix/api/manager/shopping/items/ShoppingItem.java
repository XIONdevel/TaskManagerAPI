package org.noix.api.manager.shopping.items;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.noix.api.manager.shopping.lists.ShoppingList;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shopping_item")
public class ShoppingItem {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Double price;
    private Integer quantity;
    private Boolean bought;
    @ManyToOne
    private ShoppingList list;



    public Long getListId() {
        return this.list.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingItem item)) return false;
        return Objects.equals(id, item.id) &&
                Objects.equals(name, item.name) &&
                Objects.equals(price, item.price) &&
                Objects.equals(quantity, item.quantity) &&
                Objects.equals(bought, item.bought) &&
                Objects.equals(list.getId(), item.list.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, quantity, bought, list);
    }
}
