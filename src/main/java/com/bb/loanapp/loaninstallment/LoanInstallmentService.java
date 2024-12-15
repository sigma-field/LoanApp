package com.bb.loanapp.loaninstallment;

import com.bb.loanapp.loan.model.Loan;
import com.bb.loanapp.loaninstallment.model.LoanInstallment;
import com.bb.loanapp.loaninstallment.model.PaymentResultModel;
import com.bb.loanapp.payment.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LoanInstallmentService {

    private PaymentService paymentService;
    private LoanInstallmentRepository loanInstallmentRepository;

    public void createLoanInstallments(Loan loan) {
        List<LoanInstallment> installments = new ArrayList<>();

        BigDecimal installmentAmount = loan.getLoanAmount().multiply(BigDecimal.ONE.add(loan.getInterestRate()))
                .divide(BigDecimal.valueOf(loan.getNumberOfInstallment()), 2, RoundingMode.DOWN);
        BigDecimal adjustment = loan.getLoanAmount().multiply(BigDecimal.ONE.add(loan.getInterestRate()))
                .subtract(installmentAmount.multiply(BigDecimal.valueOf(loan.getNumberOfInstallment())));

        for (int i = 0; i < loan.getNumberOfInstallment(); i++) {
            LoanInstallment loanInstallment = new LoanInstallment();
            loanInstallment.setAmount(installmentAmount);
            loanInstallment.setLoan(loan);
            loanInstallment.setDueDate(LocalDate.now().plusMonths(i + 1).withDayOfMonth(1));
            installments.add(loanInstallment);
        }

        LoanInstallment lastInstallment = installments.get(loan.getNumberOfInstallment() - 1);
        lastInstallment.setAmount(lastInstallment.getAmount().add(adjustment));

        loanInstallmentRepository.saveAll(installments);
    }


    public PaymentResultModel payInstallments(List<LoanInstallment> loanInstallments, BigDecimal paymentAmount) {

        List<LoanInstallment> unpaidInstallments = loanInstallments.stream()
                .filter(installment -> !installment.isPaid())
                .sorted(Comparator.comparing(LoanInstallment::getDueDate))
                .collect(Collectors.toList());

        List<LoanInstallment> paidInstallments = paymentService
                .payInstallments(unpaidInstallments, paymentAmount);

        PaymentResultModel result = new PaymentResultModel();
        result.setTotalNumberOfInstallmentsPaid(paidInstallments.size());
        result.setTotalAmountSpent(paidInstallments.stream()
                .map(LoanInstallment::getPaidAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return result;
    }

    public List<LoanInstallment> findInstallmentsByLoanId(long loanId) {
        return loanInstallmentRepository
                .findAllSortedByLoanId(loanId, Sort.by(Sort.Order.asc("dueDate")));
    }
}
