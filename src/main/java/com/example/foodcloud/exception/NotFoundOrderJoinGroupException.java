package com.example.foodcloud.exception;

public class NotFoundOrderJoinGroupException extends RuntimeException {
    public NotFoundOrderJoinGroupException() {
        super("Not found OrderJoinGroup");
    }
}
