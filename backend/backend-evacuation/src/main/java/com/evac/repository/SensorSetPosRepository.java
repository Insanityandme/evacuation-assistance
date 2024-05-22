package com.evac.repository;

import java.util.List;
import java.util.Optional;

import com.evac.models.SensorSetPos;
import com.evac.models.UserSensorPos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorSetPosRepository extends JpaRepository<SensorSetPos, Long> {

    Optional<SensorSetPos> findByPosition(String position);
}
