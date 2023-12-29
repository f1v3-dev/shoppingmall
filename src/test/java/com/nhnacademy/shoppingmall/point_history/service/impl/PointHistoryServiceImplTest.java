package com.nhnacademy.shoppingmall.point_history.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import com.nhnacademy.shoppingmall.point_history.domain.PointHistory;
import com.nhnacademy.shoppingmall.point_history.repository.PointHistoryRepository;
import com.nhnacademy.shoppingmall.point_history.service.PointHistoryService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
class PointHistoryServiceImplTest {

    PointHistoryRepository pointHistoryRepository = Mockito.mock(PointHistoryRepository.class);
    PointHistoryService pointHistoryService = new PointHistoryServiceImpl(pointHistoryRepository);

    PointHistory testPointHistory
            = new PointHistory(9999, "user", 1000, "테스트 포인트 내역입니다.", LocalDateTime.now());

    @Test
    @DisplayName("포인트 내역 조회 : User ID로 내역 리스트 조회")
    void getPointHistoryListByUserId() {
        Mockito.when(pointHistoryRepository.findByUserId(anyString())).thenReturn(List.of(testPointHistory));

        List<PointHistory> pointHistories = pointHistoryService.getPointHistories(testPointHistory.getUserId());
        Assertions.assertEquals(testPointHistory, pointHistories.get(0));

        Mockito.verify(pointHistoryRepository, Mockito.times(1)).findByUserId(anyString());
    }

    @Test
    @DisplayName("포인트 내역 조회 : 유효하지 않은 User ID로 조회 - 실패")
    void getPointHistoryListByInvalidUserId() {
        Mockito.when(pointHistoryRepository.findByUserId(anyString())).thenReturn(List.of());

        List<PointHistory> pointHistories = pointHistoryService.getPointHistories("INVALID_USER_ID");
        Assertions.assertEquals(0, pointHistories.size());
    }

    @Test
    @DisplayName("포인트 내역 저장 : save 테스트")
    void savePointHistory() {
        Mockito.when(pointHistoryRepository.save(any())).thenReturn(1);
        pointHistoryService.savePointHistory(testPointHistory);
        Mockito.verify(pointHistoryRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("포인트 내역 저장 : save 실패 테스트")
    void savePointHistory_Fail() {
        Mockito.when(pointHistoryRepository.save(any())).thenReturn(0);
        Throwable throwable =
                Assertions.assertThrows(RuntimeException.class, () -> pointHistoryService.savePointHistory(testPointHistory));

        log.debug("error = {}", throwable.getMessage());

        Mockito.verify(pointHistoryRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("포인트 내역 삭제 : delete 테스트")
    void deletePointHistory() {
        Mockito.when(pointHistoryRepository.deleteByPointHistoryId(anyInt())).thenReturn(1);
        pointHistoryService.deletePointHistory(testPointHistory.getPointId());
        Mockito.verify(pointHistoryRepository, Mockito.times(1)).deleteByPointHistoryId(anyInt());
    }

    @Test
    @DisplayName("포인트 내역 삭제 : delete 실패 테스트")
    void deletePointHistory_Fail() {
        Mockito.when(pointHistoryRepository.deleteByPointHistoryId(anyInt())).thenReturn(0);
        Throwable throwable =
                Assertions.assertThrows(RuntimeException.class, () -> pointHistoryService.deletePointHistory(testPointHistory.getPointId()));

        log.debug("error = {}", throwable.getMessage());

        Mockito.verify(pointHistoryRepository, Mockito.times(1)).deleteByPointHistoryId(anyInt());
    }

    @Test
    @DisplayName("포인트 내역 조회 : User ID와 내용으로 조회")
    void getPointHistoryByUserIdAndDescription() {
        Mockito.when(pointHistoryRepository.findByUserIdAndDescription(anyString(), anyString())).thenReturn(
                Optional.of(testPointHistory));

        Optional<PointHistory> pointHistory =
                pointHistoryService.getPointHistory(testPointHistory.getUserId(), testPointHistory.getDescription());
        Assertions.assertEquals(testPointHistory, pointHistory.get());

        Mockito.verify(pointHistoryRepository, Mockito.times(1)).findByUserIdAndDescription(anyString(), anyString());
    }
}