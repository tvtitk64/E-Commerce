package com.team2.fsoft.Ecommerce.exception;

public class InvalidRefreshToken extends RuntimeException{
    public  InvalidRefreshToken(String message) {
        super(message);
    }
}
