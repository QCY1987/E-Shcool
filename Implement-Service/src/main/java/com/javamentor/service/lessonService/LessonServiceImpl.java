package com.javamentor.service.lessonService;


import com.javamentor.dto.model.direction.PostLessonDto;
import com.javamentor.model.direction.Lesson;
import com.javamentor.repository.direction.DirectionRepository;
import com.javamentor.repository.lesson.LessonRepository;
import com.javamentor.repository.user.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final DirectionRepository directionRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository, DirectionRepository directionRepository, TeacherRepository teacherRepository) {
        this.lessonRepository = lessonRepository;
        this.directionRepository = directionRepository;
        this.teacherRepository = teacherRepository;
    }

    /**
     * метод получения всех уроков
     *
     * @return список всех уроков
     */
    @Override
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    /**
     * метод получения урока по id
     *
     * @param id урока
     * @return урока
     */
    @Override
    public Lesson getLessonById(Long id) {
        return lessonRepository.findById(id).orElse(null);
    }

    /**
     * проверка существования урока по id
     *
     * @param id id урока
     * @return true/false
     */
    @Override
    public boolean existLessonById(Long id) {
        return lessonRepository.existsById(id);
    }

    /**
     * метод получения нового урока из объекта
     *
     * @param postLessonDto объект с id предмета, id учителя, временем начала урока, временем окончания урока
     * @return true
     */
    @Override
    public boolean saveLessonByDTO(PostLessonDto postLessonDto) {
        Lesson lesson = new Lesson();
        lesson.setDirection(directionRepository.getDirectionById(postLessonDto.getDirectionId()));
        lesson.setTeacher(teacherRepository.getTeacherById(postLessonDto.getTeacherId()));
        lesson.setStartLesson(postLessonDto.getStartLesson());
        lesson.setEndLesson(postLessonDto.getEndLesson());
        lessonRepository.save(lesson);
        return true;
    }
}
