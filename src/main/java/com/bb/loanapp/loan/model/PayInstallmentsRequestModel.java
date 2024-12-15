package com.bb.loanapp.loan.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PayInstallmentsRequestModel {
    @Min(0)
    @NotNull
    private BigDecimal amountToPay;
}
