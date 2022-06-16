package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.RegisterUserDTO;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transfers")
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    @RequestMapping(path = "/{transferId}", method = RequestMethod.GET)
    public Transfer getTransfer(@PathVariable long transferId){
        Transfer transfer = new Transfer();

        transfer = transferDao.getTransfer(transferId);

        return transfer;
    }

    @RequestMapping(path = "/{accountId}/history", method = RequestMethod.GET)
    public List<Transfer> transferHistory(@PathVariable long accountId){
        List<Transfer> transferHistory = transferDao.transferHistory(accountId);
        return transferHistory;
    }

    @RequestMapping(path = "/send", method = RequestMethod.POST)
    public long createSendTransfer(@Valid @RequestBody Transfer transfer) {
        long result = transferDao.createTransfer(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount(), true);
        return result;
    }

    @RequestMapping(path = "/request", method = RequestMethod.POST)
    public long createRequestTransfer(@Valid @RequestBody Transfer transfer) {
        long result = transferDao.createTransfer(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount(), false);
        return result;
    }

    @RequestMapping(path = "/{transferId}/update", method = RequestMethod.PUT)
    public boolean updateTransfer(@RequestBody Transfer transfer, @PathVariable long transferId){
        boolean result = false;
        result = transferDao.updateTransfer(transfer,transferId);
        return result;

    }
}
