package com.demo.pojo;

public class TransactionStatusResponse {

    private TransactionType result;

    public TransactionStatusResponse(TransactionType result) {
        this.result = result;
    }

    public TransactionType getResult() {
        return result;
    }

    public void setResult(TransactionType result) {
        this.result = result;
    }
}
