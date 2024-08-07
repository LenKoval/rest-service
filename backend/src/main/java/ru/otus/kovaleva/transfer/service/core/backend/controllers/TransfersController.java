package ru.otus.kovaleva.transfer.service.core.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.kovaleva.transfer.service.core.api.dtos.*;
import ru.otus.kovaleva.transfer.service.core.backend.entities.Transfer;
import ru.otus.kovaleva.transfer.service.core.backend.services.TransferService;

import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transfers")
@Tag(name = "Переводы клиентов", description = "Методы работы с переводами счетов клиентов")
public class TransfersController {
    private final TransferService transferService;

    private final Function<Transfer, TransferDto> entityToDto = transfer ->
            new TransferDto(transfer.getSourceAccountNumber(), transfer.getDestinationAccountNumber()
            , transfer.getAmount(), transfer.getStatus().toString(), transfer.getDateTime());

    @Operation(summary = "Перевод между счетами")
    @PostMapping("/execute")
    public ExecuteTransferDtoResult executeTransfer(@RequestBody ExecuteTransferDtoRequest dtoRequest) {
        return transferService.transfer(dtoRequest);
    }

    @Operation(summary = "Получение информации о всех переводах пользователя")
    @GetMapping
    public TransferPageDto getAllTransfers(@RequestHeader Long clientId) {
        return new TransferPageDto(transferService.getAllTransfersById(clientId).stream()
                .map(entityToDto).collect(Collectors.toList()));
    }
}