package ru.otus.kovaleva.transfer.service.core.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.kovaleva.transfer.service.core.backend.entities.Transfer;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findAllBySourceAccountId(Long sourceAccountId);
    List<Transfer> findAllByTargetAccountId(Long targetAccountId);
}
