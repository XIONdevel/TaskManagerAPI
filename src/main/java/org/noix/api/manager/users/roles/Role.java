package org.noix.api.manager.users.roles;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public enum Role {
    ADMIN(List.of(Permission.values())),
    USER (List.of(
            Permission.READ,
            Permission.AUTHENTICATED
    ));

    private final List<Permission> permissions;

    Role(List<Permission> values) {
        this.permissions = values;
    }

    public List<Permission> getPermissions() {
        return this.permissions;
    }

    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.getPermissions().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.name())));
        return authorities;
    }
}
