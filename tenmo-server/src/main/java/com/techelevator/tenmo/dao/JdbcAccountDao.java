package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
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
    public Account getAccount(long userId){
        Account account = new Account();

        String sql = "SELECT * FROM account WHERE user_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        while(result.next()){
            account = mapRowToAccount(result);
        }

        return account;
    }

    @Override
    public long getAccountId(long userId){
        long accountId;

        String sql = "SELECT account_id FROM account WHERE user_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        accountId = result.getLong("account_id");

        return accountId;
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
    public BigDecimal getBalance(long userId) {
        BigDecimal balance = null;

        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        while(results.next()){
            Account account = mapRowToAccount(results);
            balance = account.getBalance();
        }

        return balance;

    }

    @Override
    public boolean update(long userId, Account account){
        boolean result = false;

        /*String sql = "UPDATE account SET balance = ? WHERE user_id = ?;";
        int numberOfRowsUpdated = jdbcTemplate.update(sql, account.getBalance(), userId);
        if(numberOfRowsUpdated == 1){
            result = true;
        }*/

        return result;
    }

    private Account mapRowToAccount(SqlRowSet rowSet){
        Account account = new Account();
        account.setAccountId(rowSet.getLong("account_id"));
        account.setUserId(rowSet.getLong("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }

}
