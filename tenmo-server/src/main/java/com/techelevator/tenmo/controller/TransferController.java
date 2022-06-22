package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.RegisterUserDTO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transfers")
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferDao transferDao;
    private UserDao userDao;

    public TransferController(TransferDao transferDao, UserDao userDao) {
        this.transferDao = transferDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/{transferId}", method = RequestMethod.GET)
    public Transfer getTransfer(@PathVariable long transferId, Principal principal){
        Transfer transfer = new Transfer();
        Transfer requestedTransfer = transferDao.getTransfer(transferId);

        //Check if found in database
        if(requestedTransfer.equals(null)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find details for transfer ID " + transferId);
        }

        //Set up for user identity check
        // String requestedUserNameFrom = userDao.findByAccountId(requestedTransfer.getAccountFrom()).getUsername();
        // String requestedUserNameTo = userDao.findByAccountId(requestedTransfer.getAccountTo()).getUsername();
        // String currentUserName = principal.getName();

        //Check user identity
        // if(requestedUserNameTo.equals(currentUserName) || requestedUserNameFrom.equals(currentUserName)){
            transfer = requestedTransfer;
        // }else{
        //    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authorized to access information for transfer ID " + transferId);
        // }


        return transfer;
    }

    @RequestMapping(path = "/{accountId}/history", method = RequestMethod.GET)
    public List<Transfer> transferHistory(@PathVariable long accountId, Principal principal){
        List<Transfer> transferHistory = new ArrayList<>();

        //Set up for user identity check
        // String requestedUserName = userDao.findByAccountId(accountId).getUsername();
        // String currentUserName = principal.getName();

        //Check user identity
        // if(currentUserName.equals(requestedUserName)){
            transferHistory = transferDao.transferHistory(accountId);
        //}else{
        //    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authorized to access information for account ID " + accountId);
        //}

        return transferHistory;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/send", method = RequestMethod.POST)
    public long createSendTransfer(@Valid @RequestBody Transfer transfer, Principal principal) {
        long result = 0;

        //Set up for identity check
       // String requestedUserNameFrom = userDao.findByAccountId(transfer.getAccountFrom()).getUsername();
       // String currentUserName = principal.getName();

        //Check user identity
       // if(requestedUserNameFrom.equals(currentUserName)){
            result = transferDao.createTransfer(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount(), true);
       // }else{
       //     throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authorized to create send transfer for account ID " + transfer.getAccountFrom());
       // }

        return result;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/request", method = RequestMethod.POST)
    public long createRequestTransfer(@Valid @RequestBody Transfer transfer, Principal principal) {
        long result = 0;

        //Set up for identity check
       // String requestedUserNameFrom = userDao.findByAccountId(transfer.getAccountFrom()).getUsername();
       // String currentUserName = principal.getName();

        //Check user identity
       // if(requestedUserNameFrom.equals(currentUserName)){
            result = transferDao.createTransfer(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount(), false);
       // }else{
       //     throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authorized to create request transfer for account ID " + transfer.getAccountTo());
       // }

        return result;

    }

    //Affects only the transfer status id
    @RequestMapping(path = "/{transferId}/update", method = RequestMethod.PUT)
    public boolean updateRequestTransfer(@Valid @RequestBody Transfer transfer, @PathVariable long transferId, Principal principal){
        boolean result = false;

        //Check incoming transfer status, must be request transfer type
        if(transfer.getTransferTypeId() == 2){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Send transfers cannot be updated");
        }

        //Set up for user identity check
       // String requestedFromUserName = userDao.findByAccountId(transfer.getAccountFrom()).getUsername();
       // String currentUserName = principal.getName();

        //Check user identity
       // if(requestedFromUserName.equals(currentUserName)){
            result = transferDao.updateTransfer(transfer, transferId);
       // }else{
       //     throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authorized to update request transfer for  ID " + transfer.getTransferId());
       // }

        return result;

    }
}
