package com.woori.bookspring.exception;

public class UserMissMatchException extends RuntimeException{

    public UserMissMatchException(){
        super("사용자가 일치하지 않습니다.");
    }
}
