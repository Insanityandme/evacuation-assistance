package com.evac.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evac.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);//How exactly does this method work???

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  @Override
  boolean existsById(Long aLong);
}
