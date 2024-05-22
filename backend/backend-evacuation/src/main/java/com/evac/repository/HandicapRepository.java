package com.evac.repository;

import com.evac.models.Handicap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HandicapRepository extends JpaRepository<Handicap, Long> {
    @Override
    boolean existsById(Long aLong);
}
