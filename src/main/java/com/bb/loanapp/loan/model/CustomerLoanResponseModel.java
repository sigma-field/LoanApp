package com.bb.loanapp.loan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CustomerLoanResponseModel {

    private long id;
    @JsonProperty("loan_amount")
    private BigDecimal loanAmount;
    @JsonProperty("number_of_installment")
    private int numberOfInstallment;
    @JsonProperty("create_date")
    private LocalDate createDate;
    @JsonProperty("is_paid")
    private boolean isPaid;

}
