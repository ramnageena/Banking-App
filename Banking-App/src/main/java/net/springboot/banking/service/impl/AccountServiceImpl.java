package net.springboot.banking.service.impl;

import net.springboot.banking.dto.AccountDto;
import net.springboot.banking.entity.Account;
import net.springboot.banking.repository.AccountRepository;
import net.springboot.banking.service.AccountService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = modelMapper.map(accountDto, Account.class);
        Account savedAccount = accountRepository.save(account);
        return modelMapper.map(savedAccount,AccountDto.class);

    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist by this :" + id));
        AccountDto saveAccount = modelMapper.map(account, AccountDto.class);
        return saveAccount;
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        logger.info("Starting deposit operation for account ID: {}", id);

        Account accountById = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist by this :" + id));

        logger.debug("Retrieved account details: {}", accountById);

        double totalBalAmt = accountById.getBalance() + amount;
        accountById.setBalance(totalBalAmt);

        Account savedAccount = accountRepository.save(accountById);
        logger.info("Deposit operation completed successfully for account ID: {}", id);
        return  modelMapper.map(savedAccount,AccountDto.class);

    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        AccountDto account = getAccountById(id);
        if(account.getBalance()<amount){
            throw new RuntimeException("Insufficient Amount");
        }
        double totalBalAmt = account.getBalance() - amount;
        account.setBalance(totalBalAmt);
        Account savedAccount = modelMapper.map(account, Account.class);
        return  modelMapper.map(savedAccount,AccountDto.class);

    }

    @Override
    public List<AccountDto> getAllAccount() {
        List<Account> allAccount = accountRepository.findAll();
        List<AccountDto> collectedAccount = allAccount.stream().map(account -> modelMapper.map(account, AccountDto.class)).collect(Collectors.toList());
        return collectedAccount;
    }

    @Override
    public void delete(Long id) {
        AccountDto accountById = getAccountById(id);
        Account account = modelMapper.map(accountById, Account.class);
        accountRepository.delete(account);

    }


}
