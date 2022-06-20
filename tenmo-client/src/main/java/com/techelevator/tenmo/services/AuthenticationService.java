package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.UserCredentials;

import java.util.*;

public class AuthenticationService {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();


    public AuthenticationService(String url) {
        this.baseUrl = url;
    }

    public AuthenticatedUser login(UserCredentials credentials) {
        HttpEntity<UserCredentials> entity = createCredentialsEntity(credentials);
        AuthenticatedUser user = null;
        try {
            ResponseEntity<AuthenticatedUser> response =
                    restTemplate.exchange(baseUrl + "login", HttpMethod.POST, entity, AuthenticatedUser.class);
            user = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return user;
    }

    public boolean register(UserCredentials credentials) {
        HttpEntity<UserCredentials> entity = createCredentialsEntity(credentials);
        boolean success = false;
        try {
            restTemplate.exchange(baseUrl + "register", HttpMethod.POST, entity, Void.class);
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }


    public List<String> getAllNames(){
        List<String> usernames = new ArrayList<>();
        LinkedHashMap<Long, String> userList = new LinkedHashMap();
        LinkedHashMap<Long, String> users = restTemplate.getForObject(baseUrl + "users/", LinkedHashMap.class);
        for(Map.Entry<Long, String> user: users.entrySet()){
            usernames.add(user.getValue());
        }
        return usernames;
    }
    public List<Long> getAllIds(){
        List<Long> ids = new ArrayList<>();
        LinkedHashMap<Long, String> userList = new LinkedHashMap();
        LinkedHashMap<Long, String> users = restTemplate.getForObject(baseUrl + "users/", LinkedHashMap.class);
        for(Map.Entry<Long, String> user: users.entrySet()){
            ids.add(user.getKey());
        }
        return ids;
    }
    public LinkedHashMap<String, Integer> getUsers(){
        return restTemplate.getForObject(baseUrl + "users/", LinkedHashMap.class);
    }

    public String getUsername(long userId){
        var userList = getUsersById();
        for(Map.Entry<Integer, String> entry : userList.entrySet()){
            if(entry.getKey() == userId)
                return entry.getValue();
        }

        return null;
    }

    public Long getAccountId(Long accountId){
        User user = restTemplate.getForObject(baseUrl + "/users/" + accountId, User.class);
        return user.getId();
    }

    public HashMap<Integer, String> getUsersById(){
        HashMap<Integer, String> userMap = new HashMap<>();
        for(Map.Entry<String, Integer> entry : getUsers().entrySet()){
            userMap.put(entry.getValue(), entry.getKey());

        }

        return userMap;
    }

    public void viewAll(){
        System.out.println("Available users in your network: ");
        boolean isFirst = true;
        for(String user : getAllNames()){
            if(isFirst){
                isFirst = false;
            } else{
                System.out.print(", ");
            }
            System.out.print(user);
        }
        System.out.println();
    }



    private HttpEntity<UserCredentials> createCredentialsEntity(UserCredentials credentials) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(credentials, headers);
    }
}
