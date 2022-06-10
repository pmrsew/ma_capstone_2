package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.security.jwt.TokenProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/accounts")
//@PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountDao accountDao;

    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "{userId}/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable long userId){
        Account account = accountDao.getAccount(userId);
        BigDecimal balance = account.getBalance();
        return balance;
    }

    @RequestMapping(path = "/{userId}/accountId", method = RequestMethod.GET)
    public long getAccountId(@PathVariable long userId){
        Account account = accountDao.getAccount(userId);
        long result = account.getAccountId();
        return result;
    }

    @RequestMapping(path = "/{userId}/transferHistory", method = RequestMethod.GET)
    public List<Transfer> getTransferHistory(@PathVariable long userId){
        List<Transfer> transferHistory = new ArrayList<>();

        return transferHistory;

    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    public boolean updateAccount(@RequestBody Account updatedAccount, @PathVariable long userId){
        boolean result = false;
        result = accountDao.updateAccount(updatedAccount, userId);
        return result;
    }


}
