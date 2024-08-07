package ru.otus.kovaleva.transfer.service.core.backend.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.kovaleva.transfer.service.core.api.dtos.ExecuteTransferDtoRequest;
import ru.otus.kovaleva.transfer.service.core.api.dtos.ExecuteTransferDtoResult;
import ru.otus.kovaleva.transfer.service.core.api.dtos.TransferDto;
import ru.otus.kovaleva.transfer.service.core.backend.entities.Account;
import ru.otus.kovaleva.transfer.service.core.backend.entities.Transfer;
import ru.otus.kovaleva.transfer.service.core.backend.exceptions.AppLogicException;
import ru.otus.kovaleva.transfer.service.core.backend.repositories.AccountsRepository;
import ru.otus.kovaleva.transfer.service.core.backend.repositories.TransferRepository;
import ru.otus.kovaleva.transfer.service.core.backend.validators.ExecuteTransferValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountsService accountsService;

    private final ExecuteTransferValidator executeTransferValidator;

    private final AccountsRepository accountsRepository;

    private final TransferRepository transferRepository;

    @Transactional
    public ExecuteTransferDtoResult transfer(ExecuteTransferDtoRequest dtoRequest) {

        ExecuteTransferDtoResult dtoResult = new ExecuteTransferDtoResult(dtoRequest.getSourceAccount()
                , dtoRequest.getDestinationAccount(), dtoRequest.getTransferAmount());

        BigDecimal amount = dtoRequest.getTransferAmount();

        checkTransferAmount(amount);
        checkSourceBalance(prepareSource(amount, dtoRequest), amount);

        transferRepository.save(prepareTransaction(prepareSource(amount, dtoRequest), prepareTarget(amount, dtoRequest), amount));

        return dtoResult;
    }

    public List<Transfer> getAllTransfersById(Long id) {
        List<Account> accounts = accountsRepository.findAllByClientId(id);
        List<Transfer> transfers = new ArrayList<>();
        // напрямую из бд
        return transfers;
    }

    private Account prepareSource(BigDecimal amount, ExecuteTransferDtoRequest dtoRequest) {
        Account source = accountsService.getAccountByAccountNumber(dtoRequest.getSourceAccount())
                .orElseThrow(() -> new AppLogicException("TRANSFER_SOURCE_ACCOUNT_NOT_FOUND"
                        , "Перевод невозможен поскольку не существует счет отправителя"));
        source.setBalance(source.getBalance().subtract(amount));
        return source;
    }

    private Account prepareTarget(BigDecimal amount, ExecuteTransferDtoRequest dtoRequest) {
        Account target = accountsService.getAccountByAccountNumber(dtoRequest.getDestinationAccount())
                .orElseThrow(() -> new AppLogicException("TRANSFER_TARGET_ACCOUNT_NOT_FOUND"
                        , "Перевод невозможен поскольку не существует счет получателя"));
        target.setBalance(target.getBalance().subtract(amount));
        return target;
    }

    private Transfer prepareTransaction(Account source, Account target, BigDecimal amount) {
        Transfer transfer = new Transfer(source.getId(), source.getAccountNumber(), target.getId()
                , target.getAccountNumber(), amount);
        transfer.setStatus(Transfer.Status.EXECUTED);
        return transfer;
    }

    private void checkTransferAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AppLogicException("TRANSFER_AMOUNT_NOT_POSITIVE"
                    , "Перевод невозможен поскольку сумма перевода недействительна");
        }
    }

    private void checkSourceBalance(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new AppLogicException("INSUFFICIENT_FUNDS"
                    , "Перевод невозможен поскольку недостаточно средств на счете отправителя");
        }
    }
}
