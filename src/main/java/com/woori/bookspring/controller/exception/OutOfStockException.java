package com.woori.bookspring.controller.exception;

public class OutOfStockException extends RuntimeException{

    public OutOfStockException(int stockNumber) {
        super("상품의 재고가 부족 합니다. (현재 재고 수량: " + stockNumber + ")");
    }

}