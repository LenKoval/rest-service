package ru.otus.kovaleva.transfer.service.core.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Schema(description = "ДТО для создания нового счета")
public class CreateAccountDto {

    @Schema(description = "Начальный баланс счета", required = true) //мин баланс, макс баланс??
    private BigDecimal initialBalance;
}
