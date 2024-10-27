package com.rebelatto.bankaccount.repository;


import com.rebelatto.bankaccount.models.BankAccount;
import org.springframework.data.repository.CrudRepository;

public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {

}
