package com.ilnur.bookich.dtos;

import com.ilnur.bookich.enums.ExchangeStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestStatusDTO {
    @NotBlank(message = "Please, choose the status you want to update to")
    private ExchangeStatus status;
}
