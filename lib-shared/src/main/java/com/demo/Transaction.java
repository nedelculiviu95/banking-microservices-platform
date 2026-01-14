package com.demo;

import java.time.LocalDateTime;

public class Transaction {
    private String id;
    private String serverId;
    private String account;
    private String amount;
    private LocalDateTime timestamp;

    public Transaction(String id, String serverId, String account, String amount, LocalDateTime timestamp) {
        this.id = id;
        this.serverId = serverId;
        this.account = account;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
