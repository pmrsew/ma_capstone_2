package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import org.springframework.web.client.RestTemplate;

public class TransferService {

    private String BASEURL;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;
}
