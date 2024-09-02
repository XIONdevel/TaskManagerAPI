package org.noix.api.manager.tokens;

import jakarta.transaction.Transactional;
import org.noix.api.manager.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
