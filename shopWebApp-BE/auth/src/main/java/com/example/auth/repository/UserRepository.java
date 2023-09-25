package com.example.auth.repository;

import com.example.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByLogin(String login);
    Optional<User> findUserByEmail(String email);

    @Query(nativeQuery = true, value = "SELEC * FROM users where login?1 and isLocked=false and isenabled=true")
    Optional<User> findUserByLoginAndLockAndEnabled(String login);

    Optional<User> findUserByUuid(String uid);
}
