package com.bb.loanapp.exception;

import com.bb.loanapp.exception.model.ResourceAlreadyExistsException;
import com.bb.loanapp.loan.exception.InsufficientCustomerCreditLimitException;
import com.bb.loanapp.loan.exception.InvalidLoanInstallmentNumberException;
import com.bb.loanapp.exception.model.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler  {

    @ExceptionHandler(UnsupportedOperationException.class)

    public ResponseEntity<LoanAppExceptionModel> handleUnsupportedOperationException(UnsupportedOperationException ex, WebRequest request) {

        log.error(ex.getMessage(), ex);

        LoanAppExceptionModel loanAppExceptionModel = new LoanAppExceptionModel();
        loanAppExceptionModel.setError(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
        loanAppExceptionModel.setStatusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        loanAppExceptionModel.setMessage(ex.getMessage());
        loanAppExceptionModel.setTimestamp(Instant.now());

        return ResponseEntity.unprocessableEntity().body(loanAppExceptionModel);
    }

    @ExceptionHandler(InsufficientCustomerCreditLimitException.class)
    public ResponseEntity<LoanAppExceptionModel> handleInsufficientCustomerCreditLimitException(InsufficientCustomerCreditLimitException ex, WebRequest request) {
        String message = String.format("Insufficient customer credit limit." +
                " Available: %s Demanded: %s", ex.getAvailable(), ex.getDemanded());

        log.error(message, ex);

        LoanAppExceptionModel loanAppExceptionModel = new LoanAppExceptionModel();
        loanAppExceptionModel.setError(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
        loanAppExceptionModel.setStatusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        loanAppExceptionModel.setMessage(message);
        loanAppExceptionModel.setTimestamp(Instant.now());

        return ResponseEntity.unprocessableEntity().body(loanAppExceptionModel);
    }

    @ExceptionHandler(InvalidLoanInstallmentNumberException.class)
    public ResponseEntity<LoanAppExceptionModel> handleInvalidLoanInstallmentNumberException(InvalidLoanInstallmentNumberException ex, WebRequest request) {

        String message = String.format("The installment number requested is not allowed." +
                " Requested: %s Allowed: %s",ex.getNumberOfInstallmentsDemanded(), ex.getAllowedNumberOfInstallments());

        log.error(message, ex);

        LoanAppExceptionModel loanAppExceptionModel = new LoanAppExceptionModel();
        loanAppExceptionModel.setError(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
        loanAppExceptionModel.setStatusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        loanAppExceptionModel.setMessage(message);
        loanAppExceptionModel.setTimestamp(Instant.now());

        return ResponseEntity.unprocessableEntity().body(loanAppExceptionModel);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<LoanAppExceptionModel> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {

        String message = String.format("The resource does not exists. Resource: %s, Field: %s, Value: %s"
                , ex.getResourceName(), ex.getResourceField(), ex.getFieldValue());

        log.error(message, ex);

        LoanAppExceptionModel loanAppExceptionModel = new LoanAppExceptionModel();
        loanAppExceptionModel.setError(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
        loanAppExceptionModel.setStatusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        loanAppExceptionModel.setMessage(message);
        loanAppExceptionModel.setTimestamp(Instant.now());

        return ResponseEntity.unprocessableEntity().body(loanAppExceptionModel);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<LoanAppExceptionModel> handleValidationException(MethodArgumentNotValidException ex) {
        String message = "Invalid method arguments." + ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + " " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.error(message, ex);

        LoanAppExceptionModel loanAppExceptionModel = new LoanAppExceptionModel();
        loanAppExceptionModel.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        loanAppExceptionModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
        loanAppExceptionModel.setMessage(message);
        loanAppExceptionModel.setTimestamp(Instant.now());
        return ResponseEntity.badRequest().body(loanAppExceptionModel);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<LoanAppExceptionModel> handleResourceAlreadyExistsExceptionException(ResourceAlreadyExistsException ex, WebRequest request) {

        String message = String.format("The resource already exists. Resource: %s, Field: %s, Value: %s"
                , ex.getResourceName(), ex.getFieldName(), ex.getFieldValue());

        log.error(message, ex);

        LoanAppExceptionModel loanAppExceptionModel = new LoanAppExceptionModel();
        loanAppExceptionModel.setError(HttpStatus.CONFLICT.getReasonPhrase());
        loanAppExceptionModel.setStatusCode(HttpStatus.CONFLICT.value());
        loanAppExceptionModel.setMessage(message);
        loanAppExceptionModel.setTimestamp(Instant.now());

        return ResponseEntity.unprocessableEntity().body(loanAppExceptionModel);
    }

}
