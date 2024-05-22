package com.evac.repository;

import com.evac.models.UserHandicap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserHandicapRepository extends JpaRepository<UserHandicap, Long> {

    Optional<UserHandicap> findByuserId(long id);

    boolean existsByuserId(long id);
}
