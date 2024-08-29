package ru.otus.kovaleva.transfer.service.core.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Описание перевода")
public class TransferDto {

    @Schema(description = "Номер счета отправителя", required = true, minLength = 16, maxLength = 16, example = "1234123412341234")
    private String sourceAccount;

    @Schema(description = "Номер счета получателя", required = true, minLength = 16, maxLength = 16, example = "1234123412341234")
    private String destinationAccount;

    @Schema(description = "Сумма перевода", required = true, example = "1000.99")
    private BigDecimal transferAmount;

    @Schema(description = "Статус перевода", required = true)
    private String status;

    @Schema(description = "Время последнего обновления статуса", required = true)
    private LocalDateTime updatedAt;
}
