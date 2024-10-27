package com.rebelatto.bankaccount.service;

import com.rebelatto.bankaccount.dto.DepositResponseDTO;
import com.rebelatto.bankaccount.dto.TransferResponseDTO;
import com.rebelatto.bankaccount.dto.WithdrawResponseDTO;
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
    public void resetDatabase() {
        bankAccountRepository.deleteAll();
    }

    @Transactional
    public Double getBalance(Long accountId) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("Account not found"));
        return bankAccount.getBalance();
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
    @Transactional
    public WithdrawResponseDTO withdraw(Long id, Double amount) {
        if (!bankAccountRepository.existsById(id)) {
            throw new NoSuchElementException("Bank account does not exist");
        }
        BankAccount bankAccount = bankAccountRepository.findById(id).get();
        if (bankAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
        WithdrawResponseDTO withdrawResponseDTO = new WithdrawResponseDTO();
        withdrawResponseDTO.origin = bankAccount;
        return withdrawResponseDTO;
    }

    @Transactional
    public TransferResponseDTO transfer(Long originId, Long destination, Double amount) {
        TransferResponseDTO transferResponseDTO = new TransferResponseDTO();
        transferResponseDTO.origin = withdraw(originId, amount).origin;
        transferResponseDTO.destination = deposit(destination, amount).destination;
        return transferResponseDTO;
    }

}
