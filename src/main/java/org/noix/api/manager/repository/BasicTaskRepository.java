package org.noix.api.manager.repository;

import org.noix.api.manager.dto.BasicTaskDTO;
import org.noix.api.manager.entity.BasicTask;
import org.noix.api.manager.entity.User;
import org.noix.api.manager.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
