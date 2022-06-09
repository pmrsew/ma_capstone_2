package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component //added
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();

        String sql = "SELECT account_id, user_id, balance FROM account;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            accounts.add(mapRowToAccount(results));
        }

        return accounts;
    }

    @Override
    public BigDecimal getBalance(Long userId) {
        BigDecimal balance = null;

        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?;"; //Had to add all column names
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        while(results.next()){
            Account account = mapRowToAccount(results);
            balance = account.getBalance();
        }

        return balance;

    }

    private Account mapRowToAccount(SqlRowSet rowSet){
        Account account = new Account();
        account.setAccountId(rowSet.getLong("account_id"));
        account.setUserId(rowSet.getLong("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }

}
