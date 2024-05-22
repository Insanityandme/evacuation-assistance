package com.evac.repository;

import com.evac.models.Deputy;
import com.evac.models.EvacActive;
import com.evac.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EvacActiveRepository extends JpaRepository<EvacActive, Long> {

    Optional<EvacActive> findByUsername(String username);
}

