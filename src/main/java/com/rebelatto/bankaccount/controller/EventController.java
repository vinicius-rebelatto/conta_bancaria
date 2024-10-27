package com.rebelatto.bankaccount.controller;

import com.rebelatto.bankaccount.dto.DepositResponseDTO;
import com.rebelatto.bankaccount.dto.EventRequestDTO;
import com.rebelatto.bankaccount.service.BankAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {
    private final BankAccountService bankAccountService;

    public EventController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/event")
    public ResponseEntity<DepositResponseDTO> event(@RequestBody EventRequestDTO eventRequestDTO) {
        if ("deposit".equalsIgnoreCase(eventRequestDTO.type)) {
            DepositResponseDTO response = bankAccountService.deposit(eventRequestDTO.destination, eventRequestDTO.amount);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
