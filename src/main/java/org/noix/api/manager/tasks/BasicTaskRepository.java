package org.noix.api.manager.tasks;

import org.noix.api.manager.tasks.dto.BasicTaskDTO;
import org.noix.api.manager.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasicTaskRepository extends JpaRepository<BasicTask, Long> {

    List<BasicTask> findAllByOwner(User owner);

    @Modifying
    void deleteAllByOwner(User owner);

    boolean existsByIdAndOwner(Long id, User owner);

    @Query(value = """
            SELECT new org.noix.api.manager.dto.BasicTaskDTO(bt.id, bt.name, bt.description, bt.created, bt.updated)
            FROM BasicTask bt
            WHERE bt.owner = :user
            """)
    List<BasicTaskDTO> findAllByOwnerAsDTO(@Param("user") User user);
}
