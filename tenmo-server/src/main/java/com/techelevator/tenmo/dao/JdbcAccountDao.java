package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
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
    public List<Transfer> getTransferHistory(long userId){
        List<Transfer> transferHistory = new ArrayList<>();

        String sql = "SELECT * FROM transfer WHERE account_to = ? OR account_from = ? ORDER BY transaction_id;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, getAccount(userId));
        while(results.next()){
            transferHistory.add(mapRowToTransfer(results));
        }

        return transferHistory;
    }

    @Override
    public boolean updateAccount(Account account, long userId) {
        boolean result = false;

        String sql = "UPDATE account SET balance = ? WHERE account_id = ?;";
        int returnedRows = jdbcTemplate.update(sql, account.getBalance(), userId);
        if(returnedRows == 1){
            result = true;
        }

        return result;
    }

    private Account mapRowToAccount(SqlRowSet rowSet){
        Account account = new Account();
        account.setAccountId(rowSet.getLong("account_id"));
        account.setUserId(rowSet.getLong("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet){
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getLong("transfer_id"));
        transfer.setTransferTypeId(rowSet.getLong("transfer_type_id"));
        transfer.setTransferStatusId(rowSet.getLong("transfer_status_id"));
        transfer.setAccountFrom(rowSet.getLong("account_from"));
        transfer.setAccountTo(rowSet.getLong("account_to"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        return transfer;
    }

}
