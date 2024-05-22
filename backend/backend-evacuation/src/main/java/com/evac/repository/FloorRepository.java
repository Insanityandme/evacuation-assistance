package com.evac.repository;

import java.util.Optional;

import com.evac.models.Floor;
import com.evac.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evac.models.ERole;
import com.evac.models.Role;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {
    Optional<Floor> findByName(String name);

    Boolean existsByName(String name);

    @Override
    boolean existsById(Long aLong);
}