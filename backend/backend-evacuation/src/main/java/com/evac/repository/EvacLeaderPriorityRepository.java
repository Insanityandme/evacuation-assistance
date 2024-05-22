package com.evac.repository;

import com.evac.models.EvacLeaderPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvacLeaderPriorityRepository extends JpaRepository<EvacLeaderPriority, Long> {

    @Override
    boolean existsById(Long aLong);
}
