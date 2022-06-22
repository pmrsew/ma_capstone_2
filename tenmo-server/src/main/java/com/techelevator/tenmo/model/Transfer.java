package com.techelevator.tenmo.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Transfer {

    @NotNull(message = "Transfer ID cannot be null")
    private long transferId;
    @NotNull(message = "Transfer Type ID cannot be null")
    //Add restriction for numbers allowed 1-2
    private long transferTypeId;
    @NotNull(message = "Transfer Status ID cannot be null")
    //Add restriction for numbers allowed 1-3
    private long transferStatusId;
    @NotNull(message = "Account FROM ID cannot be null")
    private long accountFrom;
    @NotNull(message = "Account TO ID cannot be null")
    private long accountTo;
    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", message = "Amount cannot be 0 or negative")
    private BigDecimal amount;

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
