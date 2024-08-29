package ru.otus.kovaleva.transfer.service.core.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Schema(description = "ДТО результат выполнения перевода")
public class ExecuteTransferDtoResult {

    @Schema(description = "Номер счета отправителя", required = true)
    private String sourceAccount;

    @Schema(description = "Номер счета получателя", required = true)
    private String destinationAccount;

    @Schema(description = "Сумма перевода", required = true)
    private BigDecimal transferAmount;

    @Schema(description = "Статус выполнения перевода")
    private String status;

    @Schema(description = "Время выполнения")
    private LocalDateTime dateTime;

    public ExecuteTransferDtoResult(String sourceAccount, String destinationAccount, BigDecimal transferAmount) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.transferAmount = transferAmount;
        this.dateTime = LocalDateTime.now();
    }

}
