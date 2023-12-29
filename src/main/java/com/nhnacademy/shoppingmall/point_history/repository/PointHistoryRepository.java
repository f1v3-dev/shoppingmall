package com.nhnacademy.shoppingmall.point_history.repository;

import com.nhnacademy.shoppingmall.point_history.domain.PointHistory;
import java.util.List;
import java.util.Optional;

public interface PointHistoryRepository {

    List<PointHistory> findByUserId(String userId);

    int save(PointHistory pointHistory);

    int deleteByPointHistoryId(int pointHistoryId);

    Optional<PointHistory> findByUserIdAndDescription(String userId, String description);
}
