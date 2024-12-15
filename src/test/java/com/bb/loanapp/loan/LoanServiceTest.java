package com.bb.loanapp.loan;

import com.bb.loanapp.customer.model.Customer;
import com.bb.loanapp.customer.CustomerService;
import com.bb.loanapp.loan.exception.InsufficientCustomerCreditLimitException;
import com.bb.loanapp.loan.exception.InvalidLoanInstallmentNumberException;
import com.bb.loanapp.loan.exception.InvalidLoanInterestRateException;
import com.bb.loanapp.loan.model.Loan;
import com.bb.loanapp.loaninstallment.LoanInstallmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Captor
    ArgumentCaptor<Loan> loanArgumentCaptor;

    @Mock
    private CustomerService customerService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanRestrictions loanRestrictions;

    @Mock
    private LoanInstallmentService loanInstallmentService;

    @InjectMocks
    private LoanService unitUnderTest;

    @Test
    void createLoanForGivenCustomer_when_loanAmountExceedsUserLimit_should_throwException() {

        Customer customer = new Customer();
        customer.setCreditLimit(BigDecimal.valueOf(100_000));
        customer.setUsedCreditLimit(BigDecimal.valueOf(25_000));

        Loan loan = new Loan();
        loan.setLoanAmount(BigDecimal.valueOf(75_001));

        when(customerService.findById(1))
                .thenReturn(Optional.of(customer));

        assertThatThrownBy(() -> unitUnderTest.createLoanForGivenCustomer(1, loan))
                .isInstanceOf(InsufficientCustomerCreditLimitException.class);

    }

    @Test
    void createLoanForGivenCustomer_when_intallmentNumberNotAllowed_should_throwException() {

        Customer customer = new Customer();
        customer.setCreditLimit(BigDecimal.valueOf(100_000));
        customer.setUsedCreditLimit(BigDecimal.valueOf(25_000));

        Loan loan = new Loan();
        loan.setLoanAmount(BigDecimal.valueOf(50_000));
        loan.setNumberOfInstallment(2);

        when(customerService.findById(1))
                .thenReturn(Optional.of(customer));
        when(loanRestrictions.allowedNumberOfInstallments())
                .thenReturn(Set.of(3,6,9,12));

        assertThatThrownBy(() -> unitUnderTest.createLoanForGivenCustomer(1, loan))
                .isInstanceOf(InvalidLoanInstallmentNumberException.class);

    }

    @Test
    void createLoanForGivenCustomer_when_interestRateOutOfRange_should_throwException() {

        Customer customer = new Customer();
        customer.setCreditLimit(BigDecimal.valueOf(100_000));
        customer.setUsedCreditLimit(BigDecimal.valueOf(25_000));

        Loan loan = new Loan();
        loan.setLoanAmount(BigDecimal.valueOf(50_000));
        loan.setNumberOfInstallment(3);
        loan.setInterestRate(BigDecimal.valueOf(0.6));

        LoanRestrictions.Range range
                = new LoanRestrictions.Range(BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.5));

        when(customerService.findById(1))
                .thenReturn(Optional.of(customer));
        when(loanRestrictions.allowedNumberOfInstallments())
                .thenReturn(Set.of(3,6,9,12));
        when(loanRestrictions.allowedInterestRateRange())
                .thenReturn(range);

        assertThatThrownBy(() -> unitUnderTest.createLoanForGivenCustomer(1, loan))
                .isInstanceOf(InvalidLoanInterestRateException.class);

    }

    @Test
    void createLoanForGivenCustomer_when_fitsAllRestrictions_should_createLoan() {

        Customer customer = new Customer();
        customer.setCreditLimit(BigDecimal.valueOf(100_000));
        customer.setUsedCreditLimit(BigDecimal.valueOf(25_000));

        Loan loan = new Loan();
        loan.setLoanAmount(BigDecimal.valueOf(50_000));
        loan.setNumberOfInstallment(3);
        loan.setInterestRate(BigDecimal.valueOf(0.5));

        LoanRestrictions.Range range
                = new LoanRestrictions.Range(BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.5));

        when(customerService.findById(1))
                .thenReturn(Optional.of(customer));
        when(loanRestrictions.allowedNumberOfInstallments())
                .thenReturn(Set.of(3,6,9,12));
        when(loanRestrictions.allowedInterestRateRange())
                .thenReturn(range);

        unitUnderTest.createLoanForGivenCustomer(1, loan);

        verify(loanRepository).save(loanArgumentCaptor.capture());
        verify(loanInstallmentService).createLoanInstallments(loan);

        assertThat(loanArgumentCaptor.getValue().getCreateDate())
                .isEqualTo(LocalDate.now());
        assertThat(loanArgumentCaptor.getValue().getCustomer().getUsedCreditLimit())
                .isEqualTo(BigDecimal.valueOf(75_000));
    }




}