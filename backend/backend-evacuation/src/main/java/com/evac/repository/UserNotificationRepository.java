package com.evac.repository;


import com.evac.models.UserNotification;

import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {
    Optional<UserNotification> findByName(String name);

    Boolean existsByName(String name);

    @Override
    boolean existsById(Long aLong);
}