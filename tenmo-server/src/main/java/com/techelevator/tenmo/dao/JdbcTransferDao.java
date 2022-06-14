package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transfer getTransfer(long transferId) {
        Transfer transfer = new Transfer();
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        while(results.next()){
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    @Override
    public List<Transfer> transferHistory(long accountId){
        List<Transfer> transferHistory = new ArrayList<>();
        String sql = "SELECT * FROM transfer WHERE account_to = ? OR account_from = ? ORDER BY transfer_id; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId, accountId);
        while(results.next()){
            transferHistory.add(mapRowToTransfer(results));
        }
        return transferHistory;
    }

    @Override
    public long createTransfer(long account_from, long account_to, BigDecimal amount, boolean isSend) {
        long transferId = 0;

        //add to transfer table
        String sql = "INSERT INTO transfer (account_from, account_to, amount, transfer_status_id, transfer_type_id) VALUES (?, ?, ?, ?, ?) RETURNING transfer_id";
//        try {
            if(isSend){
                transferId = jdbcTemplate.queryForObject(sql, Long.class, account_from, account_to, amount, 2, 2);
            }else{
                transferId = jdbcTemplate.queryForObject(sql, Long.class, account_from, account_to, amount, 1, 1);
            }
//        } catch (DataAccessException e) {
//                transferId = -1;
//        }

        return transferId;
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
