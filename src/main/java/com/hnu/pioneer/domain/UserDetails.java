package com.hnu.pioneer.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class UserDetails extends User {
    private String memberName;

    public UserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String memberName) {
        super(username, password, authorities);
        this.memberName = memberName;
    }

    public UserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, String memberName) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.memberName = memberName;
    }
}
