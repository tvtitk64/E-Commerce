package com.team2.fsoft.Ecommerce.audit_log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GlobalEventListener {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @EventListener(condition = "#event.success")
    public void handleSuccessful(GlobalSpringEvent<Object> event) {
       log.info(event.getWhat().toString());
    }
}