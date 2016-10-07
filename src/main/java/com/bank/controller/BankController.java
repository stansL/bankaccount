package com.bank.controller;

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

    public class BalanceInfo {
        String account;
        double balance;

        public BalanceInfo(String account, double balance) {
            this.account = account;
            this.balance = balance;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }
    }

    public class DepositInfo {
        String account;
        double deposit;
        double balance;

        public DepositInfo(String account, double deposit, double balance) {
            this.account = account;
            this.deposit = deposit;
            this.balance = balance;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public double getDeposit() {
            return deposit;
        }

        public void setDeposit(double deposit) {
            this.deposit = deposit;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }
    }

    public class WithdrawalInfo {
        String account;
        double withdrawn;
        double balance;

        public WithdrawalInfo(String account, double withdrawn, double balance) {
            this.account = account;
            this.withdrawn = withdrawn;
            this.balance = balance;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public double getWithdrawn() {
            return withdrawn;
        }

        public void setWithdrawn(double withdrawn) {
            this.withdrawn = withdrawn;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }
    }
    public class ApiError {

        private HttpStatus status;
        private String message;

        public ApiError(HttpStatus status, String message) {
            this.status = status;
            this.message = message;
        }

        public HttpStatus getStatus() {
            return status;
        }

        public void setStatus(HttpStatus status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
