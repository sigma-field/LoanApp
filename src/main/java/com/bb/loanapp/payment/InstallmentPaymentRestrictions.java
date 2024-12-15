package com.bb.loanapp.payment;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.math.BigDecimal;

@ConfigurationProperties("installment-payment-restrictions")
public record InstallmentPaymentRestrictions(int numberOfMaximumDueMonthsToPay, BigDecimal rewardPenaltyRate) {
    @ConstructorBinding
    public InstallmentPaymentRestrictions {
    }
}
