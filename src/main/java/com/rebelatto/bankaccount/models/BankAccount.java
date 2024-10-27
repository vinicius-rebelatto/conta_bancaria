package com.rebelatto.bankaccount.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class BankAccount {

    @Id
    private Long id;
    private Double balance;

    protected BankAccount() {}

    public BankAccount(Long id, Double balance) {
        this.id = id;
        this.balance = balance;
    }

}
