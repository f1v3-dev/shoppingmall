package com.nhnacademy.shoppingmall.point_history.service;

import com.nhnacademy.shoppingmall.point_history.domain.PointHistory;
import java.util.List;
import java.util.Optional;

public interface PointHistoryService {

    List<PointHistory> getPointHistories(String userId);

    void savePointHistory(PointHistory pointHistory);

    void deletePointHistory(int pointHistoryId);

    Optional<PointHistory> getPointHistory(String userId, String description);
}
