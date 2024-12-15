package com.bb.loanapp.loan;

import com.bb.loanapp.customer.CustomerService;
import com.bb.loanapp.exception.model.ResourceNotFoundException;
import com.bb.loanapp.customer.model.Customer;
import com.bb.loanapp.loan.exception.InsufficientCustomerCreditLimitException;
import com.bb.loanapp.loan.exception.InvalidLoanInstallmentNumberException;
import com.bb.loanapp.loan.exception.InvalidLoanInterestRateException;
import com.bb.loanapp.loan.model.Loan;
import com.bb.loanapp.loaninstallment.model.LoanInstallment;
import com.bb.loanapp.loaninstallment.LoanInstallmentService;
import com.bb.loanapp.loaninstallment.model.PaymentResultModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final CustomerService customerService;
    private final LoanRestrictions loanRestrictions;
    private final LoanInstallmentService loanInstallmentService;

    @Transactional
    public long createLoanForGivenCustomer(long customerId, Loan loan) {

        Customer customer = customerService.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("customer", "id", customerId));

        BigDecimal creditLimitAvailable = customer.getCreditLimit().subtract(customer.getUsedCreditLimit());
        BigDecimal loanAmountRequested = loan.getLoanAmount();

        if (creditLimitAvailable.compareTo(loanAmountRequested) < 0) {
            throw new InsufficientCustomerCreditLimitException(creditLimitAvailable, loanAmountRequested);
        }

        Set<Integer> allowedNumberOfInstallments = loanRestrictions.allowedNumberOfInstallments();
        int numberOfInstallmentsRequested = loan.getNumberOfInstallment();

        if (!allowedNumberOfInstallments.contains(numberOfInstallmentsRequested)) {
            throw new InvalidLoanInstallmentNumberException(allowedNumberOfInstallments, numberOfInstallmentsRequested);
        }

        LoanRestrictions.Range interestRateRangeAllowed = loanRestrictions.allowedInterestRateRange();
        BigDecimal interestRateRequested = loan.getInterestRate();

        if (!interestRateRangeAllowed.contains(interestRateRequested)) {
            throw new InvalidLoanInterestRateException(interestRateRangeAllowed, interestRateRequested);
        }

        loan.setCreateDate(LocalDate.now());

        customer.setUsedCreditLimit(customer.getUsedCreditLimit().add(loan.getLoanAmount()));

        loan.setCustomer(customer);

        loanRepository.save(loan);

        loanInstallmentService.createLoanInstallments(loan);

        return loan.getId();
    }

    public List<Loan> getLoansOfACustomer(long customerId) {
        Customer customer = customerService.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("customer", "id", customerId));

        return loanRepository.findLoansByCustomerId(customer.getId());
    }

    public void checkIfLoanExists(long loanId) {
        if (!loanRepository.existsById(loanId)) {
            throw new ResourceNotFoundException("loan", "id", loanId);
        }
        ;
    }

    public Loan getLoanById(long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("loan", "id", loanId));
    }


    public PaymentResultModel payLoanInstallments(long loanId, BigDecimal amountToPay) {
        Loan loan = getLoanById(loanId);

        if (loan.isPaid()) {
            throw new UnsupportedOperationException("Cannot pay installments. The loan is already paid up.");
        }

        List<LoanInstallment> installments = loan.getInstallments();

        PaymentResultModel paymentResultModel = loanInstallmentService
                .payInstallments(loan.getInstallments(), amountToPay);

        if (installments.stream().allMatch(LoanInstallment::isPaid)) {
            loan.setPaid(true);
            paymentResultModel.setLoanPaidUp(true);
            loan.getCustomer()
                    .setUsedCreditLimit(loan.getCustomer().getUsedCreditLimit().subtract(loan.getLoanAmount()));
        }

        loanRepository.save(loan);

        return paymentResultModel;
    }

}
