package com.nhnacademy.shoppingmall.point_history.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class PointHistory {

    /**
     *
     CREATE TABLE PointHistory
     (
     point_id INT auto_increment,
     user_id VARCHAR(50) NOT NULL,
     point INT NOT NULL,
     description VARCHAR(100) NOT NULL,
     date_updated DATETIME DEFAULT NOW(),
     CONSTRAINT pk_PointHistory PRIMARY KEY (point_id),
     CONSTRAINT fk_PointHistory_Users FOREIGN KEY (user_id) REFERENCES Users (user_id)
     );

     */

    private Integer pointId;
    private String userId;
    private Integer point;
    private String description;
    private LocalDateTime dateUpdated;

    public PointHistory(Integer pointId, String userId, Integer point, String description,
                        LocalDateTime dateUpdated) {
        this.pointId = pointId;
        this.userId = userId;
        this.point = point;
        this.description = description;
        this.dateUpdated = dateUpdated.withNano(0);
    }

    public PointHistory(String userId, int point, String description, LocalDateTime dateUpdated) {
        this(null, userId, point, description, dateUpdated);
    }

    public Integer getPointId() {
        return pointId;
    }

    public void setPointId(Integer pointId) {
        this.pointId = pointId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }


    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PointHistory that = (PointHistory) o;
        return Objects.equals(pointId, that.pointId) &&
                Objects.equals(userId, that.userId) && Objects.equals(point, that.point) &&
                Objects.equals(description, that.description) &&
                Objects.equals(dateUpdated, that.dateUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointId, userId, point, description, dateUpdated);
    }

    @Override
    public String toString() {
        return "PointHistory{" +
                "pointId=" + pointId +
                ", userId='" + userId + '\'' +
                ", point=" + point +
                ", description='" + description + '\'' +
                ", dateUpdated=" + dateUpdated +
                '}';
    }
}