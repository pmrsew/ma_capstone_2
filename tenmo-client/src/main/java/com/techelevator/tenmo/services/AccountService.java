package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import io.cucumber.java.bs.A;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

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
        balance = restTemplate.exchange(BASEURL + "accounts/" + currentUser.getUser().getId() + "/balance/",
                HttpMethod.GET,
                authHeader(),
                BigDecimal.class).getBody();
        return balance;
    }
    public Account getAccount(){

        Account account = restTemplate.exchange(BASEURL + "accounts/" + currentUser.getUser().getId() , HttpMethod.GET, authHeader(), Account.class).getBody();
        return account;
    }
    public Account getAccount(User user){
        Account account = restTemplate.exchange(BASEURL + "accounts/" + currentUser.getUser().getId() , HttpMethod.GET, authHeader(), Account.class).getBody();
        return account;
    }


    public Account updateAccount(Account account){

        account.setBalance((account.getBalance()).add(new BigDecimal(1)));
        System.out.println(account.getBalance());
        restTemplate.exchange(BASEURL + "accounts/" + currentUser.getUser().getId(), HttpMethod.PUT, authHeader(account), Void.class);


        return account;

    }





    private HttpEntity<?> authHeader(){

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
    private HttpEntity authHeader(Account account){

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(account, headers);
        return entity;
    }
}
