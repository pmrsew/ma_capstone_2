package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import javax.validation.constraints.Positive;

public class Account {

    private long accountId;
    private long userId;
    @Positive(message = "Account Balance cannot be negative")
    private BigDecimal balance;

    public long getAccountId() {
        return accountId;
    }
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
