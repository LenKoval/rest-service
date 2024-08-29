package ru.otus.kovaleva.transfer.service.core.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Информация о счетах клиента")
public class AccountsPageDto {

    @Schema(description = "Список счетов клиента", required = true)
    private List<AccountDto> items;
}
