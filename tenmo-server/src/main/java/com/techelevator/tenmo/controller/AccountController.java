package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;

@RestController
@RequestMapping("/accounts")
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountDao accountDao;
    private UserDao userDao;

    public AccountController(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    //Affects only the balance
    @PutMapping(path = "/{userId}")
    public boolean updateAccount(@Valid @RequestBody Account updatedAccount, @PathVariable long userId, Principal principal){
        boolean result = false;
       // String currentUsername = principal.getName();
       // String requestedInformationUsername = userDao.findByAccountId(accountDao.getAccount(userId).getAccountId()).getUsername();
       // if(requestedInformationUsername.equals(currentUsername)){
            result = accountDao.updateAccount(updatedAccount, userId);
       // }else{
       //     throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authorized to access information for ID " + userId);
       // }
        return result;
    }

    @GetMapping(path = "/{userId}")
    public Account getAccount(@PathVariable long userId, Principal principal){
        Account account = new Account();
        //Account ifCorrectUser = accountDao.getAccount(userId);
        //account = checkUser(principal, userId, ifCorrectUser);
        account = accountDao.getAccount(userId);
        return account;
    }

    @RequestMapping(path = "{userId}/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable long userId, Principal principal){
        Account account = new Account();
        //Account ifCorrectUser = accountDao.getAccount(userId);
        //account = checkUser(principal, userId, ifCorrectUser);
        account = accountDao.getAccount(userId);
        BigDecimal balance = account.getBalance();
        return balance;
    }

    @RequestMapping(path = "/{userId}/accountId", method = RequestMethod.GET)
    public long getAccountId(@PathVariable long userId, Principal principal){
        Account account = new Account();
        //Account ifCorrectUser = accountDao.getAccount(userId);
        //account = checkUser(principal, userId, ifCorrectUser);
        account = accountDao.getAccount(userId);
        long result = account.getAccountId();
        return result;
    }

    //User input and identity check helper method
    private Account checkUser(Principal principal, long userId, Account ifCorrectUser){
        Account account = new Account();
        if(ifCorrectUser.equals(null)){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Could not find user ID " + userId);
        }
        String currentUsername = principal.getName();
        String requestedInformationUsername = userDao.findByAccountId(accountDao.getAccount(userId).getAccountId()).getUsername();
        if(requestedInformationUsername.equals(currentUsername)){
            account = ifCorrectUser;
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authorized to access information for ID " + userId);
        }
        return account;
    }

}
