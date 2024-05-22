package com.evac.repository;

import com.evac.models.SensorSet;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface SensorSetRepository extends JpaRepository<SensorSet, Long> {
    @Transactional
    void deleteBySensorSetPosId(long sensorSetPosId);

}
