package com.bb.loanapp.loaninstallment.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentResultModel {

    private int totalNumberOfInstallmentsPaid;
    private BigDecimal totalAmountSpent;
    private boolean isLoanPaidUp;
}
