package com.rebelatto.bankaccount.service;

import com.rebelatto.bankaccount.dto.DepositResponseDTO;
import com.rebelatto.bankaccount.models.BankAccount;
import com.rebelatto.bankaccount.repository.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional
    public DepositResponseDTO deposit(Long id, Double amount) {
        if (bankAccountRepository.existsById(id)) {
            BankAccount bankAccount = bankAccountRepository.findById(id).get();
            bankAccount.setBalance(amount + bankAccount.getBalance());
            bankAccountRepository.save(bankAccount);
            DepositResponseDTO depositResponseDTO = new DepositResponseDTO();
            depositResponseDTO.destination = bankAccount;
            return depositResponseDTO;
        }
        else {
            BankAccount bankAccount = new BankAccount(id, amount);
            bankAccountRepository.save(bankAccount);
            DepositResponseDTO depositResponseDTO = new DepositResponseDTO();
            depositResponseDTO.destination = bankAccount;
            return depositResponseDTO;
        }

    }

}
