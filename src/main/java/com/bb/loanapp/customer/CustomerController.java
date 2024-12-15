package com.bb.loanapp.customer;

import com.bb.loanapp.customer.model.CreateCustomerRequestModel;
import com.bb.loanapp.customer.model.Customer;
import com.bb.loanapp.loan.model.CreateLoanForGivenCustomerRequestModel;
import com.bb.loanapp.loan.model.CustomerLoanResponseModel;
import com.bb.loanapp.loan.model.Loan;
import com.bb.loanapp.loan.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final LoanService loanService;
    private final ModelMapper modelMapper;


    @PostMapping
    @Operation(summary = "Endpoint for creating a new customer.")
    public ResponseEntity<Void> createCustomer(@Valid @RequestBody CreateCustomerRequestModel createCustomerRequestModel) {

        Customer createdCustomer = customerService
                .createCustomer(modelMapper.map(createCustomerRequestModel, Customer.class));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCustomer.getId())
                .toUri();

        return ResponseEntity.noContent().location(location).build();
    }


    @PostMapping("/{customerId}/loans")
    @Operation(summary = "Endpoint for creating a new loan for a given customer.")
    public ResponseEntity<Void> createLoanForGivenCustomer(@PathVariable long customerId, @Valid @RequestBody CreateLoanForGivenCustomerRequestModel createLoanForGivenCustomerRequestModel) {

        long createdLoanId = loanService
                .createLoanForGivenCustomer(customerId, modelMapper.map(createLoanForGivenCustomerRequestModel, Loan.class));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdLoanId)
                .toUri();

        return ResponseEntity.noContent().location(location).build();
    }

    @GetMapping("/{customerId}/loans")
    @Operation(summary = "Endpoint for listing all the loans of a given customer.")
    public ResponseEntity<List<CustomerLoanResponseModel>> listLoansOfGivenCustomer(@PathVariable long customerId) {

        List<Loan> loansOfGivenCustomer
                = loanService.getLoansOfACustomer(customerId);

        return ResponseEntity.ok(loansOfGivenCustomer.stream()
                .map(loan -> modelMapper.map(loan, CustomerLoanResponseModel.class))
                .toList());
    }

}
