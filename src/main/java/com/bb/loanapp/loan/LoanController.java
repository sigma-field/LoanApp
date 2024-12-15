package com.bb.loanapp.loan;

import com.bb.loanapp.loan.model.PayInstallmentsRequestModel;
import com.bb.loanapp.loan.model.PayInstallmentsResponseModel;
import com.bb.loanapp.loaninstallment.model.LoanInstallmentResponseModel;
import com.bb.loanapp.loaninstallment.model.LoanInstallment;
import com.bb.loanapp.loaninstallment.LoanInstallmentService;
import com.bb.loanapp.loaninstallment.model.PaymentResultModel;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
@AllArgsConstructor
public class LoanController {

    private final LoanService loanService;
    private final LoanInstallmentService loanInstallmentService;
    private final ModelMapper modelMapper;

    @Operation(summary = "Endpoint for fetching all the installments of a given loan in due date asc sorted order.")
    @GetMapping("/{loanId}/installments")
    public ResponseEntity<List<LoanInstallmentResponseModel>> listInstallmentsOfAGivenLoan(@PathVariable long loanId) {

        loanService.checkIfLoanExists(loanId);

        List<LoanInstallment> installmentsOfAGivenLoan
                = loanInstallmentService.findInstallmentsByLoanId(loanId);

        return ResponseEntity.ok(installmentsOfAGivenLoan.stream()
                .map(loan -> modelMapper.map(loan, LoanInstallmentResponseModel.class)).toList());
    }

    @Operation(summary = "Endpoint for paying the installments.")
    @PostMapping("/{loanId}/installments/payments")
    public ResponseEntity<PayInstallmentsResponseModel> payInstallmentsOfLoan(@PathVariable long loanId,
                                                                              @Valid @RequestBody PayInstallmentsRequestModel payment) {

        PaymentResultModel result = loanService.payLoanInstallments(loanId, payment.getAmountToPay());

        return ResponseEntity.ok(modelMapper.map(result, PayInstallmentsResponseModel.class));
    }
}
