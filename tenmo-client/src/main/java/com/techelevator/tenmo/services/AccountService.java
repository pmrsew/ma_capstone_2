package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
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
        balance = restTemplate.exchange(BASEURL + "accounts/balance/" + currentUser.getUser().getId() , HttpMethod.GET, authHeader(), BigDecimal.class).getBody();
        return balance;
    }

    private HttpEntity<?> authHeader(){

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
