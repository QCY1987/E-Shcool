package com.javamentor.service.lessonService;

import com.javamentor.dto.model.direction.PostLessonDto;
import com.javamentor.model.direction.Lesson;

import java.util.List;

public interface LessonService {
    List<Lesson> getAllLessons();

    Lesson getLessonById(Long id);

    boolean existLessonById(Long id);

    boolean saveLessonByDTO (PostLessonDto postLessonDto);
}
