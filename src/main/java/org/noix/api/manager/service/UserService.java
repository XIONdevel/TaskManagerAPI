package org.noix.api.manager.service;

import lombok.RequiredArgsConstructor;
import org.noix.api.manager.entity.User;
import org.noix.api.manager.entity.role.Role;
import org.noix.api.manager.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
    }

    public User createUser(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            return User.builder().username(null).build();
        } else {
            return userRepository.save(
                    User.builder()
                            .username(username)
                            .password(password)
                            .role(Role.USER)
                            .build()
            );
        }
    }
}
