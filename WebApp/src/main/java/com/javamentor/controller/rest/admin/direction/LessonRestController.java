package com.javamentor.controller.rest.admin.direction;

import com.javamentor.dto.model.direction.PostLessonDto;
import com.javamentor.model.direction.LessonInterval;
import com.javamentor.service.lessonService.LessonIntervalService;
import com.javamentor.service.lessonService.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/lesson")
public class LessonRestController {

    private final LessonService lessonService;
    private final LessonIntervalService lessonIntervalService;

    @Autowired
    public LessonRestController(LessonService lessonService, LessonIntervalService lessonIntervalService) {
        this.lessonService = lessonService;
        this.lessonIntervalService = lessonIntervalService;
    }

    /**
     * метод вывода всех уроков
     *
     * @return все уроки
     */
    @GetMapping
    public ResponseEntity<?> getLessonList() {
        return ResponseEntity.ok(lessonService.getAllLessons());
    }

    /**
     * метод создания нового урока
     *
     * @param postLessonDto передается объект, содержащий в себе id предмета, id учителя, время начала урока, время окончания урока
     * @return статус
     */
    @PostMapping
    public ResponseEntity<?> saveNewLesson(@RequestBody PostLessonDto postLessonDto) {
        final boolean saved = lessonService.saveLessonByDTO(postLessonDto);
        return saved
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    /**
     * метод вывода урока по id
     *
     * @param lessonId id урока
     * @return статус
     */
    @GetMapping("/{lessonId}")
    public ResponseEntity<?> getLessonById(@PathVariable(name = "lessonId") Long lessonId) {
        if (lessonService.existLessonById(lessonId)) {
            return ResponseEntity.ok(lessonService.getLessonById(lessonId));
        }
        return new ResponseEntity<>("Запрашиваемый контент не найден", HttpStatus.NO_CONTENT);
    }

    /**
     * метод возвращает все интервалы
     *
     * @return Список интервалов
     */
    @GetMapping("/lessonInterval/all")
    public ResponseEntity<?> getAllLessonInterval() {
        return ResponseEntity.ok(lessonIntervalService.getAllIntervals());
    }


    /**
     * метод создания нового интервала урока
     *
     * @param lessonInterval - передается объект, содержащий в себе id интервала урока,
     *                       время начала интервала, время окончания интервала
     * @return статус
     */

    @PostMapping("/lessonInterval/add")
    public ResponseEntity<?> createNewLessonInterval(@RequestBody LessonInterval lessonInterval) {
        if (lessonIntervalService.createNewLessonInterval(lessonInterval)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Интервалы не должны пересекаться, проверьте корректность запроса",
                HttpStatus.BAD_REQUEST);
    }
}
