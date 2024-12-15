package com.bb.loanapp.customer.model;

import com.bb.loanapp.loan.model.Loan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private BigDecimal creditLimit;
    private BigDecimal usedCreditLimit = BigDecimal.ZERO;
    @OneToMany(mappedBy = "customer")
    private Set<Loan> loans;
}
