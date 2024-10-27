package com.rebelatto.bankaccount.service;

import com.rebelatto.bankaccount.dto.TransactionResponseDTO;
import com.rebelatto.bankaccount.models.BankAccount;
import com.rebelatto.bankaccount.repository.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional
    public TransactionResponseDTO deposit(Long id, Double amount) {
        if (bankAccountRepository.existsById(id)) {
            BankAccount bankAccount = bankAccountRepository.findById(id).get();
            bankAccount.setBalance(amount + bankAccount.getBalance());
            bankAccountRepository.save(bankAccount);
            TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
            transactionResponseDTO.destination = bankAccount;
            return transactionResponseDTO;
        }
        else {
            BankAccount bankAccount = new BankAccount(id, amount);
            bankAccountRepository.save(bankAccount);
            TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
            transactionResponseDTO.destination = bankAccount;
            return transactionResponseDTO;
        }
    }
    @Transactional
    public TransactionResponseDTO withdraw(Long id, Double amount) {
        if (!bankAccountRepository.existsById(id)) {
            throw new NoSuchElementException("Bank account does not exist");
        }
        BankAccount bankAccount = bankAccountRepository.findById(id).get();
        if (bankAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
        transactionResponseDTO.destination = bankAccount;
        return transactionResponseDTO;
    }

}
