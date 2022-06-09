package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    //Just methods

    List<Account> getAllAccounts();

    BigDecimal getBalance(Long accountId);

}
