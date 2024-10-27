package com.rebelatto.bankaccount.repository;


import com.rebelatto.bankaccount.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

}
