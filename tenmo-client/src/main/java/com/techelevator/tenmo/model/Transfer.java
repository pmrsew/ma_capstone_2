
package com.techelevator.tenmo.model;

import javax.annotation.processing.Generated;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class Transfer {

    //generate ID on this one
    private long transferId;
    private long transferTypeId;
    private long transferStatusId;
    private long accountFrom;
    private long accountTo;
    private Account fromAccount;
    private Account toAccount;
    private User userFrom;
    private User userTo;
    private BigDecimal amount;

    public Transfer(){}

    public Transfer(Account fromAccount, Account toAccount, BigDecimal amount){
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.accountFrom = fromAccount.getAccountId();
        this.accountTo = toAccount.getAccountId();
        this.amount = amount;
    }

    public long getTransferId() {
        return transferId;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }

    public long getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public long getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(long transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(long accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
