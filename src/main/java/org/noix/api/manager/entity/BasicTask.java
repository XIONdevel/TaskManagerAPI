package org.noix.api.manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.noix.api.manager.entity.task.Task;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "basic_task")
public class BasicTask implements Task, Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    @JoinColumn(name = "owner_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    private Date created;
    private Date updated;

    public void update() {
        this.updated = new Date();
    }

    private void setUpdated(Date updated) {
        this.updated = updated;
    }

    public BasicTask(String name, String description, User owner) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.created = new Date();
        this.updated = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicTask basicTask)) return false;
        return Objects.equals(id, basicTask.id) &&
                Objects.equals(name, basicTask.name) &&
                Objects.equals(description, basicTask.description) &&
                Objects.equals(owner, basicTask.owner) &&
                Objects.equals(created, basicTask.created) &&
                Objects.equals(updated, basicTask.updated);
    }

    @Override
    public int hashCode() {
        return 67 * Objects.hash(name, description, owner.getUsername(), created, updated);
    }
}
