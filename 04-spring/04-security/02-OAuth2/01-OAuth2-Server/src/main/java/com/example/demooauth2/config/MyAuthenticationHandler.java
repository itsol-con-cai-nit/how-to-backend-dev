package com.example.demooauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpSession;

@Configuration
public class MyAuthenticationHandler {

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            HttpSession session = request.getSession();
            String clientId = (String) session.getAttribute("client_id");
            String responseType = (String) session.getAttribute("response_type");
            String redirectUri = (String) session.getAttribute("redirect_uri");
            String scope = (String) session.getAttribute("scope");
            String state = (String) session.getAttribute("state");
            session.setAttribute("authentication", authentication);
            session.removeAttribute("client_id");
            session.removeAttribute("response_type");
            session.removeAttribute("redirect_uri");
            session.removeAttribute("scope");
            session.removeAttribute("state");
            response.sendRedirect("/oauth/authorize?client_id=" + clientId + "&response_type=" + responseType +
                    "&redirect_uri=" + redirectUri + "&scope=" + scope + "&state=" + state);
        };
    }
}
