package com.example.demooauth2.service;

import com.example.demooauth2.model.AccessToken;

public interface OAuth2Service {

    AccessToken getAccessToken(String clientId, String clientSecret, String redirectUri, String code, String state);
}
