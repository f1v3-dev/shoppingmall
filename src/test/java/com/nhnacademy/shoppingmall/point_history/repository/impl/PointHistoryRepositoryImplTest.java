package com.nhnacademy.shoppingmall.point_history.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.point_history.domain.PointHistory;
import com.nhnacademy.shoppingmall.point_history.repository.PointHistoryRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
class PointHistoryRepositoryImplTest {

    PointHistoryRepository pointHistoryRepository = new PointHistoryRepositoryImpl();

    PointHistory testPointHistory;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        testPointHistory = new PointHistory(9999, "user", 1000, "테스트 포인트 내역입니다.", LocalDateTime.now());
        log.debug("testPointHistory: {}", testPointHistory);
        pointHistoryRepository.save(testPointHistory);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("포인트 내역 조회 : 회원 ID로 포인트 내역 리스트 조회")
    void findByUserId() {
        List<PointHistory> pointList = pointHistoryRepository.findByUserId(testPointHistory.getUserId());
        assertEquals(testPointHistory, pointList.get(0));
    }

    @Test
    @DisplayName("포인트 내역 저장 : save 테스트")
    void savePointHistory() {
        PointHistory newPointHistory = new PointHistory(9999, "user", 1000, "테스트2 내역입니다!!!!", LocalDateTime.now());
        int result = pointHistoryRepository.save(newPointHistory);
        assertEquals(1, result);
    }

    @Test
    @DisplayName("포인트 내역 찾기 : User ID와 내용으로 조회 - 시간 오차범위로 실패할 수 있음..")
    void findByUserIdAndDescription() {
        Optional<PointHistory> pointHistory =
                pointHistoryRepository.findByUserIdAndDescription(testPointHistory.getUserId(),
                        testPointHistory.getDescription());

        assertEquals(testPointHistory, pointHistory.get());
    }

    @Test
    @DisplayName("포인트 내역 찾기 : 실패 테스트")
    void findByUserIdAndDescription_Fail() {
        Optional<PointHistory> pointHistory =
                pointHistoryRepository.findByUserIdAndDescription(testPointHistory.getUserId(),
                        "이 내용은 없을걸");

        assertEquals(Optional.empty(), pointHistory);
    }


}