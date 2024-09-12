package org.noix.api.manager.shopping.lists;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.noix.api.manager.users.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shopping_list")
public class ShoppingList {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne
    private User owner;


    public ShoppingList(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public Long getOwnerId() {
        return owner.getId();
    }
}
