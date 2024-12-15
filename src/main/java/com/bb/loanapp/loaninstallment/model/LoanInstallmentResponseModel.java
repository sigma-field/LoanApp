package com.bb.loanapp.loaninstallment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LoanInstallmentResponseModel {
    private int id;
    private BigDecimal amount;
    @JsonProperty("paid_amount")
    private BigDecimal paidAmount;
    @JsonProperty("due_date")
    private LocalDate dueDate;
    @JsonProperty("payment_date")
    private LocalDate paymentDate;
    @JsonProperty("is_paid")
    private boolean isPaid;
}
