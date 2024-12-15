package com.bb.loanapp.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String resourceField;
    private Object fieldValue;
}
