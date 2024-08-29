package ru.otus.kovaleva.transfer.service.core.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Schema(description = "ДТО запроса выполнения перевода")
public class ExecuteTransferDtoRequest {

    @Schema(description = "Номер счета отправителя", required = true, minLength = 16
            , maxLength = 16, example = "1234123412341234")
    private String sourceAccount;

    @Schema(description = "Номер счета получателя", required = true, minLength = 16
            , maxLength = 16, example = "1234123412341234")
    private String destinationAccount;

    @Schema(description = "Сумма перевода", required = true, example = "1000.99")
    private BigDecimal transferAmount;

}
