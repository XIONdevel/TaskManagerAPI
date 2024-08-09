package org.noix.api.manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.noix.api.manager.entity.task.Task;

import java.util.Date;


@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "basic_task")
public class BasicTask implements Task {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    @Column(name = "owner_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    private Date created;
    private Date updated;


}
