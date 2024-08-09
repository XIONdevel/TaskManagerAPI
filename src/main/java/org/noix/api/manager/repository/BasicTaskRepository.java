package org.noix.api.manager.repository;

import org.noix.api.manager.entity.BasicTask;
import org.noix.api.manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasicTaskRepository extends JpaRepository<BasicTask, Long> {

    List<BasicTask> findAllByOwner(User owner);

    void deleteAllByOwner(User owner);

}
