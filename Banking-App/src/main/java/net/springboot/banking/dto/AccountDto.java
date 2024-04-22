package net.springboot.banking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    @Schema(
            description = "Account Holder Name"
    )
    private String accountHolderName;
    @Schema(
            description = "Total Balance"
    )
    private double balance;
}
