package com.javamentor.service.lessonService;

import com.javamentor.model.direction.LessonInterval;
import com.javamentor.repository.lesson.LessonIntervalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class LessonIntervalServiceImpl implements LessonIntervalService {

    private final LessonIntervalRepository lessonIntervalRepository;

    @Autowired
    public LessonIntervalServiceImpl(LessonIntervalRepository lessonIntervalRepository) {
        this.lessonIntervalRepository = lessonIntervalRepository;
    }

    /**
     * метод создания нового интервала на основании класса LessonInterval
     * с проверкой на пересечение интервалов
     *
     * @return true или false
     */
    @Override
    public boolean createNewLessonInterval(LessonInterval lessonInterval) {
        LocalTime startNewInterval = lessonInterval.getStartInterval();
        LocalTime endNewInterval = lessonInterval.getEndInterval();
        if (startNewInterval.isAfter(endNewInterval)) {
            return false;
        }
        List<LessonInterval> lessonIntervals = getAllIntervals();
        if (!lessonIntervals.isEmpty()) {
            for (LessonInterval interval : lessonIntervals) {
                LocalTime startIntervalFromList = interval.getStartInterval();
                LocalTime endIntervalFromList = interval.getEndInterval();
                if (startNewInterval.isAfter(startIntervalFromList) && startNewInterval.isBefore(endIntervalFromList)) {
                    return false;
                }
                if (endNewInterval.isAfter(startIntervalFromList) && endNewInterval.isBefore(endIntervalFromList)) {
                    return false;
                }
            }
        }
        lessonIntervalRepository.save(lessonInterval);
        return true;
    }

    /**
     * метод получения всех интервалов
     *
     * @return список всех интервалов
     */
    @Override
    public List<LessonInterval> getAllIntervals() {
        return lessonIntervalRepository.findAll();
    }
}
