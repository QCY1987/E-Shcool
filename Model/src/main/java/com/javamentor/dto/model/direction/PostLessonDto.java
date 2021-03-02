package com.javamentor.dto.model.direction;

import java.time.LocalDateTime;

public class PostLessonDto {
    Long directionId;
    Long teacherId;
    LocalDateTime startLesson;
    LocalDateTime endLesson;

    public PostLessonDto() {
    }

    public PostLessonDto(Long directionId, Long teacherId, LocalDateTime startLesson, LocalDateTime endLesson) {
        this.directionId = directionId;
        this.teacherId = teacherId;
        this.startLesson = startLesson;
        this.endLesson = endLesson;
    }

    public Long getDirectionId() {
        return directionId;
    }

    public void setDirectionId(Long directionId) {
        this.directionId = directionId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public LocalDateTime getStartLesson() {
        return startLesson;
    }

    public void setStartLesson(LocalDateTime startLesson) {
        this.startLesson = startLesson;
    }

    public LocalDateTime getEndLesson() {
        return endLesson;
    }

    public void setEndLesson(LocalDateTime endLesson) {
        this.endLesson = endLesson;
    }
}
