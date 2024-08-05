package ru.otus.kovaleva.transfer.service.core.backend.validators;

import ru.otus.kovaleva.transfer.service.core.api.dtos.ExecuteTransferDtoRequest;
import ru.otus.kovaleva.transfer.service.core.backend.exceptions.FieldValidationError;
import ru.otus.kovaleva.transfer.service.core.backend.exceptions.FieldsValidationException;

import java.util.ArrayList;
import java.util.List;

public class ExecuteTransferValidator {

    public void validate(ExecuteTransferDtoRequest request) {
        List<FieldValidationError> errorFields = new ArrayList<>();



        if (!errorFields.isEmpty()) {
            throw new FieldsValidationException(errorFields);
        }
    }
}