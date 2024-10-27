package com.rebelatto.bankaccount.controller;

import com.rebelatto.bankaccount.dto.TransactionResponseDTO;
import com.rebelatto.bankaccount.dto.EventRequestDTO;
import com.rebelatto.bankaccount.service.BankAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
public class EventController {
    private final BankAccountService bankAccountService;

    public EventController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/event")
    public ResponseEntity<?> event(@RequestBody EventRequestDTO request) {
        switch (request.type){
            case "deposit":
                TransactionResponseDTO depositResponseDTO  = bankAccountService.deposit(request.destination, request.amount);
                return new ResponseEntity<>(depositResponseDTO , HttpStatus.OK);
            case "withdraw":
                try {
                    TransactionResponseDTO withdrawalResponseDTO  = bankAccountService.withdraw(request.origin, request.amount);
                    return new ResponseEntity<>(withdrawalResponseDTO , HttpStatus.CREATED);
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
