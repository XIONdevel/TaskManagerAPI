package org.noix.api.manager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Column(unique = true)
    private String jwt;
    private Date expiration;
}
