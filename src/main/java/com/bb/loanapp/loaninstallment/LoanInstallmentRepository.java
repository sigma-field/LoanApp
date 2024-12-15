package com.bb.loanapp.loaninstallment;

import com.bb.loanapp.loaninstallment.model.LoanInstallment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanInstallmentRepository extends JpaRepository<LoanInstallment,Long> {

    List<LoanInstallment> findAllSortedByLoanId(long loanId, Sort sort);
}
