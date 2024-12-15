package com.bb.loanapp.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

import java.time.Instant;

@Getter
@Setter
public class LoanAppExceptionModel {
    private int statusCode;
    private String error;
    private String message;
    private Instant timestamp;
}
