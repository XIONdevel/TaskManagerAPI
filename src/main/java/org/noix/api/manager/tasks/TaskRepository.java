package org.noix.api.manager.tasks;

import org.noix.api.manager.tasks.dto.TaskDTO;
import org.noix.api.manager.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByOwner(User owner);

    Optional<Task> findByIdAndOwner(Long id, User owner);

    @Modifying
    void deleteAllByOwner(User owner);

    boolean existsByIdAndOwner(Long id, User owner);

    @Query(value = """
            SELECT new org.noix.api.manager.tasks.dto.TaskDTO(t.id, t.name, t.description, t.created, t.updated)
            FROM Task t
            WHERE t.owner = :user
            """)
    List<TaskDTO> findAllByOwnerAsDTO(@Param("user") User user);
}
