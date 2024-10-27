package com.rebelatto.bankaccount.controller;

import com.rebelatto.bankaccount.dto.DepositResponseDTO;
import com.rebelatto.bankaccount.dto.EventRequestDTO;
import com.rebelatto.bankaccount.dto.TransferResponseDTO;
import com.rebelatto.bankaccount.dto.WithdrawResponseDTO;
import com.rebelatto.bankaccount.service.BankAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
public class EventController {
    private final BankAccountService bankAccountService;

    public EventController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetDatabase() {
        bankAccountService.resetDatabase();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(@RequestParam Long accountId) {
        try {
            Double balance = bankAccountService.getBalance(accountId);
            return new ResponseEntity<>(balance, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/event")
    public ResponseEntity<?> event(@RequestBody EventRequestDTO request) {
        switch (request.type){
            case "deposit":
                DepositResponseDTO depositResponseDTO  = bankAccountService.deposit(request.destination, request.amount);
                return new ResponseEntity<>(depositResponseDTO , HttpStatus.OK);
            case "withdraw":
                try {
                    WithdrawResponseDTO withdrawResponseDTO  = bankAccountService.withdraw(request.origin, request.amount);
                    return new ResponseEntity<>(withdrawResponseDTO , HttpStatus.OK);
                } catch (NoSuchElementException e) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } catch (IllegalArgumentException e) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            case "transfer":
                try {
                    TransferResponseDTO transferResponseDTO  = bankAccountService.transfer(request.origin, request.destination, request.amount);
                    return new ResponseEntity<>(transferResponseDTO , HttpStatus.OK);
                } catch (NoSuchElementException e) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } catch (IllegalArgumentException e) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
