package org.noix.api.manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne()
    private User user;
    @Column(unique = true)
    private String jwt;
    private Date expiration;

    public boolean isExpired() {
        return this.expiration.before(new Date(System.currentTimeMillis()));
    }
}
