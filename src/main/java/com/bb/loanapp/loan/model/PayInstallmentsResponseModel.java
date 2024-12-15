package com.bb.loanapp.loan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PayInstallmentsResponseModel {
    @JsonProperty("total_number_of_installments_paid")
    private int totalNumberOfInstallmentsPaid;
    @JsonProperty("total_amount_spent")
    private BigDecimal totalAmountSpent;
    @JsonProperty("is_loan_paid_up")
    private boolean isLoanPaidUp;
}
