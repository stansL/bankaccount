package com.bank.controller;

import com.bank.exceptions.ApiError;
import com.bank.model.BalanceInfo;
import com.bank.model.DepositInfo;
import com.bank.model.WithdrawalInfo;
import com.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class BankController {
    @Autowired
    AccountService accountService;

    @RequestMapping(value = "{account}/balance", method = RequestMethod.GET)
    public ResponseEntity<BalanceInfo> getBalance(@PathVariable String account) {
        double balance = accountService.getBalance(account);
        return ResponseEntity.ok(new BalanceInfo(account, balance));
    }

    @RequestMapping(value = "{account}/deposit", method = RequestMethod.POST)
    public ResponseEntity<Object> deposit(@PathVariable String account, @RequestParam double amount) {
        try {
            double balance = accountService.deposit(account, amount);
            return ResponseEntity.ok(new DepositInfo(account, amount, balance));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.FORBIDDEN, e.getMessage()),HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "{account}/withdraw", method = RequestMethod.POST)
    public ResponseEntity<Object> withdraw(@PathVariable String account, @RequestParam double amount) {
        try {
            double balance = accountService.withdraw(account, amount);
            return ResponseEntity.ok(new WithdrawalInfo(account, amount, balance));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.FORBIDDEN, e.getMessage()),HttpStatus.FORBIDDEN);
        }
    }

  
}
