package com.midas.midashackathon.domain.user.facade;

import com.midas.midashackathon.domain.user.entity.UserEntity;
import com.midas.midashackathon.domain.user.exception.UserUnauthorizedException;
import com.midas.midashackathon.domain.user.repository.UserRepository;
import com.midas.midashackathon.global.security.UserAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class UserFacade {
    private UserRepository userRepository;

    @Transactional
    public UserEntity queryCurrentUser() {
        try {
            UserAuthentication authentication = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
            return userRepository.findById(authentication.getUserId())
                    .orElseThrow();
        } catch (Exception exception) {
            throw UserUnauthorizedException.EXCEPTION;
        }
    }

}
