package com.demo.service;

import com.demo.entity.Ip;
import com.demo.entity.UserAccount;
import com.demo.exception.DuplicateResourceException;
import com.demo.repo.IpRepository;
import com.demo.repo.UserAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IpService {

    private final IpRepository repo;

    public IpService(IpRepository repo) {
        this.repo = repo;
    }

    public List<Ip> getAllIps() {
        return repo.findAll();
    }

    public Ip createNewIp(String ipValue) {
        return (Ip) repo.findByValue(ipValue)
                .map(existing -> { throw new DuplicateResourceException("IP address already exists: " + ipValue); })
                .orElseGet(() -> repo.save(new Ip(ipValue)));
    }

    @Transactional
    public long deleteIp(String ipValue) {
        return this.repo.deleteByValue(ipValue);
    }

}
