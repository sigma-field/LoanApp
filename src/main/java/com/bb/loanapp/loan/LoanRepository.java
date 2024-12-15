package com.bb.loanapp.loan;

import com.bb.loanapp.loan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findLoansByCustomerId(long customerId);
}
