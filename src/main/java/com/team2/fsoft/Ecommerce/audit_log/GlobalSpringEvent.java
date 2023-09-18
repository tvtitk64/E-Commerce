package com.team2.fsoft.Ecommerce.audit_log;

import lombok.Data;

@Data
public class GlobalSpringEvent<T> {
    private T what;
    protected boolean success;

    public GlobalSpringEvent(T what, boolean success) {
        this.what = what;
        this.success = success;
    }

}