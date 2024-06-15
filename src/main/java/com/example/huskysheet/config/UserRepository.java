package com.example.huskysheet.config;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This Interface helps find users by their usernames.
 *
 * @author Katie w
 */

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
  UserEntity findByUsername(String username);
}
