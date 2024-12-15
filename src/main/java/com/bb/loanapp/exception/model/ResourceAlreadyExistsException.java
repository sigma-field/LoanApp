package com.bb.loanapp.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResourceAlreadyExistsException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private String fieldValue;
}
