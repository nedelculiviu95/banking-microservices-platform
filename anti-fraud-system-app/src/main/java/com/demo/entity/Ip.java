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
@Table(name = "ip")
public class Ip {

    @Id
    @Column(name = "ip_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // H2 auto-increment
    private long id;

    @Column(nullable = false, name = "ip_val")
    private String value;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;


    public Ip(String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
