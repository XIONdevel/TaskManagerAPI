package org.noix.api.manager.shopping.items;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.noix.api.manager.shopping.list.ShoppingList;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shopping_item")
public class ShoppingItem {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private Boolean bought;
//TODO: add method for preparing to save
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id")
    private ShoppingList list;

    public ShoppingItem(String name, Double price, Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.bought = false;
    }
}
