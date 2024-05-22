package com.evac.repository;

import com.evac.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This is a repository of the token-table
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Boolean existsByEmail(String email);

    Boolean existsByToken(String token);

}
