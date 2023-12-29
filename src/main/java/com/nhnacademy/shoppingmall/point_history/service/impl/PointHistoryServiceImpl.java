package com.nhnacademy.shoppingmall.point_history.service.impl;

import com.nhnacademy.shoppingmall.point_history.domain.PointHistory;
import com.nhnacademy.shoppingmall.point_history.repository.PointHistoryRepository;
import com.nhnacademy.shoppingmall.point_history.service.PointHistoryService;
import java.util.List;
import java.util.Optional;

public class PointHistoryServiceImpl implements PointHistoryService {

    private PointHistoryRepository pointHistoryRepository;

    public PointHistoryServiceImpl(PointHistoryRepository pointHistoryRepository) {
        this.pointHistoryRepository = pointHistoryRepository;
    }

    @Override
    public List<PointHistory> getPointHistories(String userId) {
        return pointHistoryRepository.findByUserId(userId);
    }

    @Override
    public void savePointHistory(PointHistory pointHistory) {
        int result = pointHistoryRepository.save(pointHistory);
        if (result < 1) {
            throw new RuntimeException("포인트 내역 저장 실패");
        }
    }

    @Override
    public void deletePointHistory(int pointHistoryId) {
        int result = pointHistoryRepository.deleteByPointHistoryId(pointHistoryId);
        if (result < 1) {
            throw new RuntimeException("포인트 내역 삭제 실패");
        }
    }

    @Override
    public Optional<PointHistory> getPointHistory(String userId, String description) {

        return pointHistoryRepository.findByUserIdAndDescription(userId, description);
    }
}
