package net.springboot.banking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.springboot.banking.dto.AccountDto;
import net.springboot.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.Double;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/banking")
@Tag(
        name="Banking-App",
        description = "Simple Banking App"
)
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Operation(
            summary = "New Account",
            description = "To create a new account for a customer."
    )
    @PostMapping
    ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto){
        AccountDto account = accountService.createAccount(accountDto);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }
    @Operation(
            summary = "Get Account",
            description = "To retrieve an account for a customer."
    )
    @GetMapping("/{id}")
    ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
        AccountDto accountById = accountService.getAccountById(id);
        return  new ResponseEntity<>(accountById,HttpStatus.FOUND);
    }

    @Operation(
            summary = " Deposit Amount",
            description = "To deposit an amount into a customer's account."
    )
    @PutMapping("/{id}/deposit")
    ResponseEntity<AccountDto> deposit(@PathVariable Long id, @RequestBody Map<String, Double> request){
        Double amount =  request.get("amount");
        AccountDto deposit = accountService.deposit(id, amount);
        return new ResponseEntity<>(deposit, HttpStatus.OK);
    }
    @Operation(
            summary = "Withdraw Amount",
            description = "To withdraw an amount from the account."
    )
    @PutMapping("/{id}/withdraw")
    ResponseEntity<AccountDto> withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request){
        Double amount =  request.get("amount");
        AccountDto deposit = accountService.withdraw(id, amount);
        return new ResponseEntity<>(deposit, HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Account",
            description = "To retrieve all accounts of a customer."
    )
@GetMapping
    ResponseEntity<List<AccountDto>> getAllAccount(){
        List<AccountDto> allAccount = accountService.getAllAccount();
        return ResponseEntity.ok(allAccount);
    }

    @Operation(
            summary = "Delete Account",
            description = "To delete an account for a customer."
    )
    @DeleteMapping("/{id}")
    ResponseEntity<AccountDto> deleteAccount(@PathVariable Long id){
       accountService.delete(id);
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
