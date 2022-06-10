package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import io.cucumber.java.bs.A;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
        balance = restTemplate.exchange(BASEURL + "accounts/" + currentUser.getUser().getId() + "/balance/" , HttpMethod.GET, authHeader(), BigDecimal.class).getBody();
        return balance;
    }

//    public Transfer sendFunds(User userFrom, User userTo, BigDecimal amount){
//        Transfer currentTransfer = new Transfer(userFrom, userTo, amount);
//        BigDecimal currentBalance = getBalance();
//        long accountFromID =restTemplate.exchange(BASEURL + "accounts/" + userFrom.getId() + "/accountId/" , HttpMethod.GET, authHeader(), Long.class).getBody();
//        long accountToID =restTemplate.exchange(BASEURL + "accounts/" + userTo.getId() + "/accountId/" , HttpMethod.GET, authHeader(), Long.class).getBody();
//
//        if(currentBalance.compareTo(amount) == -1){
//            return null;
//        } else{
//
//        }
//        account.addTransfer(currentTransfer);
//
//
//        return currentTransfer;
//    }

    public Account changeAccountValue(){
        Account changeAccount =new Account();

    }




    private HttpEntity<?> authHeader(){

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
