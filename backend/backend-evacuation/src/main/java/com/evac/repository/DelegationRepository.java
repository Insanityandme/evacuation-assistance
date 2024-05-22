package com.evac.repository;

import java.util.Optional;

import com.evac.models.Delegation;
import com.evac.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.evac.models.ERole;
import com.evac.models.Role;

@Repository
public interface DelegationRepository extends JpaRepository<Delegation, Long> {
    Optional<Delegation> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Modifying
    @Query("DELETE FROM Delegation u WHERE u.username = :username")
    void deleteByUsername(String username);

    @Override
    boolean existsById(Long aLong);

}
