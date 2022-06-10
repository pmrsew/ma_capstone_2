package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    private String BASEURL;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;
    private Account account;

    public AccountService(String url, AuthenticatedUser currentUser){
        BASEURL = url;
        this.currentUser = currentUser;
    }
    public BigDecimal getBalance(){

        BigDecimal balance = new BigDecimal(0);
        balance = restTemplate.exchange(BASEURL + "accounts/" + currentUser.getUser().getId() + "/balance/" , HttpMethod.GET, authHeader(), BigDecimal.class).getBody();
        return balance;
    }

    public Transfer sendFunds(User userFrom, User userTo, BigDecimal amount){
        Transfer currentTransfer = new Transfer(userFrom, userTo, amount);
        account.addTransfer(currentTransfer);


        return currentTransfer;
    }

    private HttpEntity<?> authHeader(){

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
