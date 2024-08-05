package ru.otus.kovaleva.transfer.service.core.backend.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.kovaleva.transfer.service.core.backend.entities.Account;
import ru.otus.kovaleva.transfer.service.core.backend.exceptions.AppLogicException;
import ru.otus.kovaleva.transfer.service.core.backend.validators.ExecuteTransferValidator;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountsService accountsService;

    private final ExecuteTransferValidator executeTransferValidator;

    @Transactional
    public void transfer(Long sourceAccountId, Long targetAccountId) {
        Account source = accountsService.getAccountById(1L, sourceAccountId).orElseThrow(() -> new AppLogicException("TRANSFER_SOURCE_ACCOUNT_NOT_FOUND", "Перевод невозможен поскольку не существует счет отправителя"));
        Account target = accountsService.getAccountById(1L, targetAccountId).orElseThrow(() -> new AppLogicException("TRANSFER_TARGET_ACCOUNT_NOT_FOUND", "Перевод невозможен поскольку не существует счет получателяч"));

    }
}
