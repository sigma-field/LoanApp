package com.bb.loanapp.loan.model;

import com.bb.loanapp.customer.model.Customer;
import com.bb.loanapp.loaninstallment.model.LoanInstallment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Customer customer;
    private BigDecimal loanAmount;
    private int numberOfInstallment;
    private LocalDate createDate;
    private boolean isPaid;
    @Transient
    private BigDecimal interestRate;
    @OneToMany(mappedBy = "loan")
    private List<LoanInstallment> installments;
}
