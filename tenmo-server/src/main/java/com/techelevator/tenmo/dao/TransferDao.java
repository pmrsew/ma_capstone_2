package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    Transfer getTransfer(long transferId);
    boolean create(long account_from, long account_to, BigDecimal amount, boolean isSend);

}
