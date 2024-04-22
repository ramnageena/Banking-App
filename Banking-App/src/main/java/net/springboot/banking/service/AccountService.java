package net.springboot.banking.service;

import net.springboot.banking.dto.AccountDto;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);
    AccountDto deposit(Long id,double amount);
    AccountDto withdraw(Long id,double amount);

    List<AccountDto>getAllAccount();
    void delete(Long id);

}
