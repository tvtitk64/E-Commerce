package com.team2.fsoft.Ecommerce.audit_log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class SecurityAuditListener {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @EventListener
    @Async
    public void handleAuthenticationSuccess(AuthenticationSuccessEvent event) {

        String username = event.getAuthentication().getName();
        String message = "User " + username + " logged in successfully.";
        log.info(message);

    }

    @EventListener
    @Async
    public void handleAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {

        String username = (String) event.getAuthentication().getPrincipal();
        String message = "Failed login attempt for user " + username;
        log.error(message);

    }
}