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

@RestController
@RequestMapping("/transfers")
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    public Transfer getTransfer(@PathVariable long transferId){
        Transfer transfer = new Transfer();

        transfer = transferDao.getTransfer(transferId);

        return transfer;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void createSendTransfer(@Valid @RequestBody Transfer transfer) {
        if (!transferDao.create(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount(), true)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer creation failed");
        }
    }

}
