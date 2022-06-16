package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import io.cucumber.java.bs.A;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
