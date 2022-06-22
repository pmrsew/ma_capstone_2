package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import io.cucumber.java.bs.A;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class TransferService {

    private String BASEURL;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;
    private AccountService accountService;
    private AuthenticationService authenticationService;

    public TransferService(String url, AuthenticatedUser currentUser){
        BASEURL = url + "/transfers";
        this.currentUser = currentUser;
        accountService = new AccountService(url, currentUser);
        authenticationService = new AuthenticationService(url);
    }

    public boolean sendCash(Transfer transfer){

        Account accountTo = transfer.getToAccount();
        Account accountFrom = transfer.getFromAccount();
        if(accountFrom.getBalance().compareTo(transfer.getAmount()) <=0 || accountFrom == accountTo || transfer.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            return false;
        }

        accountService.updateAccount(accountFrom, transfer.getAmount().negate());

        accountService.updateAccount(accountTo, transfer.getAmount());
        Long value = restTemplate.exchange(BASEURL + "/send", HttpMethod.POST, authHeader(transfer), Long.class).getBody();


        return true;
    }

    public Transfer[] getPastTransfers(){
        return restTemplate.exchange(BASEURL + "/" + accountService.getAccount().getAccountId() + "/history/", HttpMethod.GET, authHeader(), Transfer[].class).getBody();
    }

    public Transfer getTransferById(int id) {
        return restTemplate.exchange(BASEURL + "/" + id , HttpMethod.GET, authHeader(), Transfer.class).getBody();
    }

    public String transferDetails(Transfer transfer, AuthenticationService authenticationService){
            StringBuilder sb = new StringBuilder("");

            long toAccountId = transfer.getAccountTo();
            long toUserId = authenticationService.getAccountId(toAccountId);
            long fromAccountId = transfer.getAccountFrom();
            long fromUserId = authenticationService.getAccountId(fromAccountId);
            sb.append(String.format("-----------------\n"));
            sb.append(String.format("Transfer ID: %d\n", transfer.getTransferId()));
            sb.append(String.format("From %s to %s\n", authenticationService.getUsername(fromUserId), authenticationService.getUsername(toUserId)));
            sb.append(String.format("Amount: %.2f\n", transfer.getAmount()));

            return sb.toString();
    }




    private HttpEntity authHeader(Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(transfer, headers);
        return entity;
    }

    private HttpEntity<?> authHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
