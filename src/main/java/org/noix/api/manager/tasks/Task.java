package org.noix.api.manager.tasks;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.noix.api.manager.tasks.dto.CreateTaskRequest;
import org.noix.api.manager.users.User;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class Task implements Serializable {

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

    public boolean isNameValid() {
        if (name == null) return false;
        return name.length() >= 6 &&
                name.length() <= 30;
    }

    public Long getOwnerId() {
        return this.owner.getId();
    }

    public void update() {
        this.updated = new Date();
    }

    private void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Task(CreateTaskRequest request, User owner) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.owner = owner;
        this.created = new Date();
        this.updated = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return Objects.equals(id, task.id) &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(owner, task.owner) &&
                Objects.equals(created, task.created) &&
                Objects.equals(updated, task.updated);
    }

    @Override
    public int hashCode() {
        return 67 * Objects.hash(name, description, owner.getUsername(), created, updated);
    }
}
