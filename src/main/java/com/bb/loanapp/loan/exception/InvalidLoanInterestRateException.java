package com.bb.loanapp.loan.exception;

import com.bb.loanapp.loan.LoanRestrictions;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class InvalidLoanInterestRateException extends RuntimeException {

    private LoanRestrictions.Range interestRateRangeAllowed;
    private BigDecimal interestRateDemanded;
}
