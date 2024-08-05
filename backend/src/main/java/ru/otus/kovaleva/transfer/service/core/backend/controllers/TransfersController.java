package ru.otus.kovaleva.transfer.service.core.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.kovaleva.transfer.service.core.backend.services.TransferService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transfers")
public class TransfersController {
    private final TransferService transferService;

    @PostMapping("/execute")
    public void executeTransfer() {
        transferService.transfer(2L, 3L);
    }
}