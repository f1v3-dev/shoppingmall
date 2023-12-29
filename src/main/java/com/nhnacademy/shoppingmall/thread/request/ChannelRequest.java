package com.nhnacademy.shoppingmall.thread.request;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 포인트 적립이라는 요청
 */
public abstract class ChannelRequest {
    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private final long requestId;

    protected ChannelRequest(){
        requestId = ID_GENERATOR.incrementAndGet();
    }
    public abstract void execute();

    @Override
    public String toString() {
        return "ChannelRequest{" +
                "requestId=" + requestId +
                '}';
    }
}