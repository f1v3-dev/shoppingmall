package com.nhnacademy.shoppingmall.thread.request.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.point_history.domain.PointHistory;
import com.nhnacademy.shoppingmall.point_history.repository.impl.PointHistoryRepositoryImpl;
import com.nhnacademy.shoppingmall.point_history.service.PointHistoryService;
import com.nhnacademy.shoppingmall.point_history.service.impl.PointHistoryServiceImpl;
import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PointChannelRequest extends ChannelRequest {

    private String userId;
    private int totalPoint;
    private String description;

    public PointChannelRequest(String userId, int totalPrice, String description) {
        this.userId = userId;
        this.totalPoint = totalPrice / 10;
        this.description = description;
    }

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private final PointHistoryService pointHistoryService =
            new PointHistoryServiceImpl(new PointHistoryRepositoryImpl());


    @Override
    public void execute() {
        DbConnectionThreadLocal.initialize();
        //todo#14-5 포인트 적립구현, connection은 point적립이 완료되면 반납합니다.
        log.debug("pointChannel execute");

        try {
            userService.withdraw(userId, totalPoint);
            pointHistoryService.savePointHistory(
                    new PointHistory(userId, totalPoint, description, LocalDateTime.now()));


        } catch (Exception e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException(e.getMessage());
        } finally {
            DbConnectionThreadLocal.reset();
        }
    }
}
