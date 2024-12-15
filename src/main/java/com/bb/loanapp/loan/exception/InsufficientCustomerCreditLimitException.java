package com.bb.loanapp.loan.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
@AllArgsConstructor
@Getter
public class InsufficientCustomerCreditLimitException extends RuntimeException {

    private BigDecimal available;
    private BigDecimal demanded;
}
