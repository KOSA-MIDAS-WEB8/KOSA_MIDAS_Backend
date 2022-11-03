package com.midas.midashackathon.global.security;

import com.midas.midashackathon.domain.user.entity.UserEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {
    public UserAuthentication(UserEntity user) {
        super(user, null, List.of(
                (GrantedAuthority) () -> String.format("ROLE_%s", user.getRole().name())
        ));
    }
}
