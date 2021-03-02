package com.javamentor.controller.rest.admin.schedule;

import com.javamentor.service.scheduleService.ScheduleService;
import com.javamentor.service.studentClassService.StudentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("api/admin/schedule")
public class ScheduleRestController {

    private final StudentClassService studentClassService;
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleRestController(StudentClassService studentClassService,
                                  ScheduleService scheduleService) {
        this.studentClassService = studentClassService;
        this.scheduleService = scheduleService;
    }

    /**
     * метод возвращает уроки за неделю.
     *
     * @return - объект класса ScheduleStudentClassDto, который содержит List уроков за неделю
     * @param studentClassId - передается id класса
     * @param localDate - передается дата, по которой определяется неделя
     */

    @GetMapping("/{studentClassId}")
    public ResponseEntity<?> getScheduleStudentClassDtoByStudentClassId
    (@PathVariable(name = "studentClassId") Long studentClassId,
     @RequestParam("localDate")
     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        if (studentClassService.existStudentClassById(studentClassId)) {
            return ResponseEntity.ok(scheduleService
                    .getScheduleStudentClassByStudentClass(studentClassId, localDate));
        }
        return new ResponseEntity<>("Запрашиваемый контент не найден", HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<?> createBlankScheduleForWeak(@RequestParam("localDate")
                                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        //TODO метод должен принимать дату. на основании даты вычислять начало недели
        // и создать пустое расписание для всех классов с Пн по Пт. Должны быть созданы и добавлены в базу уроки без Учителя и Направления.
        // Количество уроков на день соответствует количеству интервалов.
        return null;
    }
}
