package com.bb.loanapp.payment;

import com.bb.loanapp.loaninstallment.model.LoanInstallment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {

    private InstallmentPaymentRestrictions installmentPaymentRestrictions;

    public List<LoanInstallment> payInstallments(List<LoanInstallment> unpaidLoanInstallments, BigDecimal amountToPay) {

        List<LoanInstallment> paidInstallments = new ArrayList<>();

        BigDecimal remainingAmount = amountToPay;

        for (LoanInstallment loanInstallment : unpaidLoanInstallments) {
            if (remainingAmount.compareTo(loanInstallment.getAmount()) > -1 &&
                    LocalDate.now().plusMonths(installmentPaymentRestrictions.numberOfMaximumDueMonthsToPay() - 1)
                            .isAfter(loanInstallment.getDueDate())) {
                loanInstallment.setPaid(true);
                loanInstallment.setPaymentDate(LocalDate.now());
                loanInstallment.setPaidAmount(calculatePaymentAmount(loanInstallment));
                remainingAmount = remainingAmount.subtract(loanInstallment.getAmount());
                paidInstallments.add(loanInstallment);
            }
        }

        return paidInstallments;
    }

    private BigDecimal calculatePaymentAmount(LoanInstallment loanInstallment) {
        return loanInstallment.getAmount()
                .multiply(BigDecimal.ONE
                        .subtract(installmentPaymentRestrictions.rewardPenaltyRate().multiply(BigDecimal.valueOf(
                                ChronoUnit.DAYS.between(LocalDate.now(), loanInstallment.getDueDate())))));
    }
}
