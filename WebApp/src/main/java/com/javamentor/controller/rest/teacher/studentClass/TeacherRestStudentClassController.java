package com.javamentor.controller.rest.teacher.studentClass;

import com.javamentor.model.user.Teacher;
import com.javamentor.service.studentClassService.StudentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/teacher/studentClass")
public class TeacherRestStudentClassController {

    @Autowired
    public StudentClassService studentClassService;

    @GetMapping()
    public ResponseEntity<?> getStudentClassByCurator(@AuthenticationPrincipal Teacher teacher) {
        //TODO метод возвращает List<StudentClassDto> в которых текущий преподаватель является куратором.
        // Классы вместе с учениками
            return ResponseEntity.ok().body(studentClassService.getListStudentsClassByCurator(teacher.getId()));
    }
}
