package com.evac.repository;

import com.evac.models.UserSensorPos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
public interface UserSensorPosRepository extends JpaRepository<UserSensorPos, Long> {

    boolean existsByUsername(String username);

    @Modifying
    @Query("DELETE FROM UserSensorPos u WHERE u.username = :username")
    void deleteByUsername(String username);

    Optional<UserSensorPos> findByUsername(String username);
}
