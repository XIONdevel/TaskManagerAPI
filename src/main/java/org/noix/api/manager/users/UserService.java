package org.noix.api.manager.users;

import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.noix.api.manager.security.jwt.JwtService;
import org.noix.api.manager.users.roles.Role;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
    }

    // No token validation, use only for AUTHENTICATED endpoints
    public User getUserFromRequest(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(jwt);
        return loadUserByUsername(username);
    }

    public Optional<User> createUser(String username, String password) {
        if (userRepository.existsByUsername(username)) { //TODO: replace
            return Optional.empty();
        } else {
            User user = userRepository.save(
                    User.builder()
                            .username(username)
                            .password(password)
                            .role(Role.USER)
                            .build()
            );
            return Optional.of(user);
        }
    }
}
