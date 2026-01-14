package com.demo.repo;

import com.demo.entity.Ip;
import com.demo.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IpRepository extends JpaRepository<Ip, Long> {

    public Optional<Ip> findByValue(String value);

    long deleteByValue(String value);
}
