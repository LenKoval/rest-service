package ru.otus.kovaleva.transfer.service.core.backend.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.kovaleva.transfer.service.core.api.dtos.ExecuteTransferDtoRequest;
import ru.otus.kovaleva.transfer.service.core.backend.entities.Account;
import ru.otus.kovaleva.transfer.service.core.backend.entities.Transfer;
import ru.otus.kovaleva.transfer.service.core.backend.exceptions.AppLogicException;
import ru.otus.kovaleva.transfer.service.core.backend.repositories.TransferRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountsService accountsService;

    private final TransferRepository transferRepository;

    @Transactional
    public Transfer transfer(ExecuteTransferDtoRequest dtoRequest) {

        BigDecimal amount = dtoRequest.getTransferAmount();

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AppLogicException("TRANSFER_AMOUNT_NOT_POSITIVE"
                    , "Перевод невозможен поскольку сумма перевода недействительна");
        }

        Account source = accountsService.getAccountByAccountNumber(dtoRequest.getSourceAccount())
                .orElseThrow(() -> new AppLogicException("TRANSFER_SOURCE_ACCOUNT_NOT_FOUND"
                        , "Перевод невозможен поскольку не существует счет отправителя"));
        source.setBalance(source.getBalance().subtract(amount));

        if (source.getBalance().compareTo(amount) < 0) {
            throw new AppLogicException("INSUFFICIENT_FUNDS"
                    , "Перевод невозможен поскольку недостаточно средств на счете отправителя");
        }

        Account target = accountsService.getAccountByAccountNumber(dtoRequest.getDestinationAccount())
                .orElseThrow(() -> new AppLogicException("TRANSFER_TARGET_ACCOUNT_NOT_FOUND"
                        , "Перевод невозможен поскольку не существует счет получателя"));
        target.setBalance(target.getBalance().subtract(amount));

        Transfer transfer = new Transfer(source.getId(), source.getAccountNumber(), target.getId()
                , target.getAccountNumber(), amount);
        transfer.setStatus(Transfer.Status.EXECUTED);


        return transfer;
    }

    public List<Transfer> getAllTransfersByClientId(Long id) {
        return transferRepository.findAllByClientId(id);
    }
}
