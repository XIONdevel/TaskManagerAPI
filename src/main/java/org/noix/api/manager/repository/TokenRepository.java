package org.noix.api.manager.repository;

import jakarta.transaction.Transactional;
import org.noix.api.manager.entity.Token;
import org.noix.api.manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> getTokenByJwt(String jwt);

    List<Token> getAllByUser(User user);

    @Modifying
    @Transactional
    void deleteAllByUser(User user);

    @Modifying
    @Transactional
    void deleteByJwt(String jwt);

}
