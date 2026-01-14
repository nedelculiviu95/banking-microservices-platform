package com.demo.repo;

import com.demo.entity.StolenCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StolenCardRepository extends JpaRepository<StolenCard, Long> {

    public Optional<StolenCardRepository> findByNumber(String number);
}
