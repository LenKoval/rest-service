package ru.otus.kovaleva.transfer.service.core.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.kovaleva.transfer.service.core.api.dtos.*;
import ru.otus.kovaleva.transfer.service.core.backend.entities.Transfer;
import ru.otus.kovaleva.transfer.service.core.backend.services.TransferService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transfers")
@Tag(name = "Переводы клиентов", description = "Методы работы с переводами счетов клиентов")
public class TransfersController {
    private final TransferService transferService;

    @Operation(summary = "Перевод между счетами")
    @PostMapping("/execute")
    public ExecuteTransferDtoResult executeTransfer(@RequestBody ExecuteTransferDtoRequest dtoRequest) {
        return toDto(transferService.transfer(dtoRequest));
    }

    @Operation(summary = "Получение информации о всех переводах пользователя")
    @GetMapping
    public TransferPageDto getAllTransfers(@RequestHeader Long clientId) {
        return toDto(transferService.getAllTransfersByClientId(clientId));
    }

    private ExecuteTransferDtoResult toDto(Transfer transfer) {
        return new ExecuteTransferDtoResult(transfer.getSourceAccountNumber(),
                transfer.getDestinationAccountNumber(),
                transfer.getAmount());
    }

    private TransferPageDto toDto(List<Transfer> list) {
        return new TransferPageDto(list.stream()
                .map(transfer ->
                        new TransferDto(transfer.getSourceAccountNumber(),
                                transfer.getDestinationAccountNumber(),
                                transfer.getAmount(),
                                transfer.getStatus().toString(),
                                transfer.getDateTime())).toList());
    }
}