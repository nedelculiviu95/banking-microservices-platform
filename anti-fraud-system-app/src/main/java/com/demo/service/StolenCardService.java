package com.demo.service;

import com.demo.entity.StolenCard;
import com.demo.repo.StolenCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StolenCardService {

    private StolenCardRepository repo;

    public StolenCardService(StolenCardRepository repo) {
        this.repo = repo;
    }

    public List<StolenCard> readAll() {
        return this.repo.findAll();
    }

    public StolenCard saveNumber(String number) {
        Optional<StolenCardRepository> stolenCard = this.repo.findByNumber(number);
        if (stolenCard.isPresent()) {
            return null;
        }

        return this.repo.save(new StolenCard(number));
    }
}
