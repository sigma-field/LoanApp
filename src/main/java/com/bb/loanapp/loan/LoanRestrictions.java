package com.bb.loanapp.loan;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.math.BigDecimal;
import java.util.Set;

@ConfigurationProperties("loan-restrictions")
public record LoanRestrictions(Set<Integer> allowedNumberOfInstallments, Range allowedInterestRateRange) {

    @ConstructorBinding
    public LoanRestrictions {
    }

    public record Range(BigDecimal lowerBound, BigDecimal upperBound) {

        public boolean contains(BigDecimal value) {
            return lowerBound.compareTo(value) < 1 && upperBound.compareTo(value) > -1;
        }
    }
}
