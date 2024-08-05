package ru.otus.kovaleva.transfer.service.core.backend.exceptions;

import java.util.List;

public class FieldsValidationException extends RuntimeException {
    private List<FieldValidationError> fields;

    public List<FieldValidationError> getFields() {
        return fields;
    }

    public FieldsValidationException(List<FieldValidationError> fields) {
        this.fields = fields;
    }
}
