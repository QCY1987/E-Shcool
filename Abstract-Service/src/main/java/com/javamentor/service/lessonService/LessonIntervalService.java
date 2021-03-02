package com.javamentor.service.lessonService;

import com.javamentor.model.direction.LessonInterval;

import java.util.List;

public interface LessonIntervalService {

    boolean createNewLessonInterval(LessonInterval lessonInterval);

    List<LessonInterval> getAllIntervals();
}
