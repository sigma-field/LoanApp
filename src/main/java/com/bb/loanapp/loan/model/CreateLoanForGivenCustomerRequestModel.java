package com.bb.loanapp.loan.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateLoanForGivenCustomerRequestModel {

    @Min(0)
    @NotNull
    private BigDecimal loanAmount;
    @Min(0)
    @NotNull
    private int numberOfInstallment;
    @Min(0)
    @NotNull
    private BigDecimal interestRate;
}
