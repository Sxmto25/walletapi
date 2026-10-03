package com.somto.walletapi.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private BigDecimal balance;
    private String currency;
    private LocalDateTime createdAt;

    public Wallet(){}

    public Wallet(Long id, Long userId, BigDecimal balance, String currency, LocalDateTime createdAt){
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.currency = currency;
        this.createdAt = createdAt;
    }

    public Long getId(){
        return id;
    }
    public Long getUserId(){
        return userId;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public String getCurrency(){
        return currency;
    }
    public LocalDateTime getCreatedAt(){return createdAt;}

    public void setUserId(Long userId){
        this.userId = userId;
    }
    public void setBalance(BigDecimal balance){
        this.balance = balance;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
