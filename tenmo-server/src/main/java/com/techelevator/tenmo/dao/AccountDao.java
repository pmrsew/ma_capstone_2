package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    long getAccountId(long userId);

    List<Account> getAllAccounts();

    BigDecimal getBalance(long userId);

    boolean update(long userId, Account account);

}
