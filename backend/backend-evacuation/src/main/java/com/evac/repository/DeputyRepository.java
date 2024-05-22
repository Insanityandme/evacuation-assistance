/**
 * This package contains all the repositories that will work together with the different tables in the database. Thanks to
 * these repositories we are allowed to save and get data from the database by using java-objects
 */
package com.evac.repository;

import com.evac.models.Deputy;
import com.evac.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeputyRepository extends JpaRepository<Deputy, Long> {

    Optional<Deputy> findByUsername(String username);
}

