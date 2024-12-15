package com.bb.loanapp.loan.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class InvalidLoanInstallmentNumberException extends RuntimeException {

    private final Set<Integer> allowedNumberOfInstallments;
    private final int numberOfInstallmentsDemanded;

}
