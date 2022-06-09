package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.security.jwt.TokenProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController //Added
@RequestMapping("/accounts")
@PreAuthorize("isAuthenticated()") //Added
public class AccountController {

    private AccountDao accountDao;

    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "/balance/{userId}", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable long userId){
        BigDecimal balance = null;

        balance = accountDao.getBalance(userId);
        //I forget to set method to the balance
        //Now that it is correctly set, data is being pulled

        return balance;
    }
}
