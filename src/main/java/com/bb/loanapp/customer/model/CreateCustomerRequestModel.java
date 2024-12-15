package com.bb.loanapp.customer.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateCustomerRequestModel {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @Min(0)
    @NotNull
    private BigDecimal creditLimit;
}
