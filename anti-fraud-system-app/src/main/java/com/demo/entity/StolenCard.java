package com.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "stolen_card")
public class StolenCard {

    @Id
    @Column(name = "stolen_card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // H2 auto-increment
    private long id;

    @Column(nullable = false, name = "stolen_card_number")
    private String number;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    public StolenCard(String number) {
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
