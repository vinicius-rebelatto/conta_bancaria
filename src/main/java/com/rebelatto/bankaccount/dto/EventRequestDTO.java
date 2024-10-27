package com.rebelatto.bankaccount.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventRequestDTO {
    @JsonProperty("type")
    public String type;

    @JsonProperty("destination")
    public Long destination;

    @JsonProperty("amount")
    public Double amount;
}
