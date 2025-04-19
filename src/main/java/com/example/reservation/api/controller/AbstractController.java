package com.example.reservation.api.controller;

import com.example.reservation.infrastructure.persistence.entity.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractController {

    protected Long getCurrentUserId() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(object instanceof UserEntity)) {
            return null;
        }
        return ((UserEntity) object).getId();
    }

    protected UserEntity getCurrentUser() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(object instanceof UserEntity)) {
            return null;
        }
        return (UserEntity) object;
    }

}
