package com.demo.repo;

import com.demo.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    int deleteByUsername(String username); // returns number of deleted rows

    Optional<UserAccount> findByUsername(String username);
}
