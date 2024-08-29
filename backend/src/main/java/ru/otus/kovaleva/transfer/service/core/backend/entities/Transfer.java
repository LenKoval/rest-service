package ru.otus.kovaleva.transfer.service.core.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "source_client_id")
    private Long sourceAccountId;

    @Column(name = "source_account_number")
    private String sourceAccountNumber;

    @Column(name = "destination_client_id")
    private Long destinationAccountId;

    @Column(name = "destination_account_number")
    private String destinationAccountNumber;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "status")
    private Status status;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime dateTime;

    public Transfer(Long sourceAccountId, String sourceAccountNumber, Long destinationAccountId
            , String destinationAccountNumber, BigDecimal amount) {
        this.sourceAccountId = sourceAccountId;
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountId = destinationAccountId;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
        status = Status.CREATED;
    }

    public enum Status {

        CREATED,
        IN_PROCESSING,
        EXECUTED,
        ERROR
    }
}
