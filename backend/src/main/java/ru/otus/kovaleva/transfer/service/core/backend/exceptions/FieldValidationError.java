package ru.otus.kovaleva.transfer.service.core.backend.exceptions;

public class FieldValidationError {
    private String fieldName;
    private String message;

    public FieldValidationError(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}
