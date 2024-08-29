package ru.otus.kovaleva.transfer.service.core.backend.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.kovaleva.transfer.service.core.api.dtos.CreateAccountDto;
import ru.otus.kovaleva.transfer.service.core.backend.entities.Account;
import ru.otus.kovaleva.transfer.service.core.backend.exceptions.AppLogicException;
import ru.otus.kovaleva.transfer.service.core.backend.repositories.AccountsRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountsService {

    private final AccountsRepository accountsRepository;

    private static final Logger logger = LoggerFactory.getLogger(AccountsService.class.getName());

    public List<Account> getAllAccounts(Long clientId) {
        return accountsRepository.findAllByClientId(clientId);
    }

    public Optional<Account> getAccountById(Long clientId, Long id) {
        return accountsRepository.findByIdAndClientId(id, clientId);
    }

    public Optional<Account> getAccountByAccountNumber(String accountNumber){
        return accountsRepository.findByAccountNumber(accountNumber);
    }

    public Account createNewAccount(Long clientId, CreateAccountDto createAccountDto) {
        if (createAccountDto.getInitialBalance().compareTo(BigDecimal.ZERO) <= 0 || createAccountDto.getInitialBalance() == null) {
            throw new AppLogicException("VALIDATION_ERROR", "Создаваемый счет не может иметь 0 или меньше баланс");
        }

        if (createAccountDto.getInitialBalance().compareTo(BigDecimal.valueOf(1_000_000L)) > 1_000_000L) {
            throw new AppLogicException("VALIDATION_ERROR", "Создаваемый баланс счета не может быть больше 1_000_000");
        }
        Account account = new Account(clientId, createAccountDto.getInitialBalance());
        account.setAccountNumber(createAccountNumber());
        account = accountsRepository.save(account);
        logger.info("Account id = {} created from {}", account.getId(), createAccountDto);
        return account;
    }

    private String createAccountNumber() {
        int maximum = 16;

        String s = "1234123412341234";
        StringBuffer number = new StringBuffer();

        for (int i = 0; i < maximum; i++) {
            number.append(s.charAt(new Random().nextInt(s.length())));
        }
        return number.toString();
    }
}
