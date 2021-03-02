package com.javamentor.controller.rest.admin.user;


import com.javamentor.model.user.User;
import com.javamentor.service.adminService.UserService;
import com.javamentor.dto.model.teacher.TeacherDto;
import com.javamentor.service.teacherService.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/admin/teacher")
public class TeacherRestController {

    private final TeacherService teacherService;
    private final UserService userService;

    @Autowired
    public TeacherRestController(TeacherService teacherService, UserService userService) {
        this.teacherService = teacherService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getTeachersList() {
        return ResponseEntity.ok(teacherService.getAllTeacherDto());
    }

    @GetMapping("/all/curators")
    public ResponseEntity<?> getTeacherListIsCurator() {
        return ResponseEntity.ok(teacherService.getAllTeacherDtoIsCurator());
    }

    @GetMapping("/all/notcurators")
    public ResponseEntity<?> getTeacherListIsNotCurator() {
        return ResponseEntity.ok(teacherService.getAllTeacherDtoIsNotCurator());
    }

    @GetMapping("/all/{search}")
    public ResponseEntity<?> getTeachersListBySearch(@PathVariable String search) {
        return ResponseEntity.ok(teacherService.getAllTeacherDtoBySearch(search));
    }

    @PostMapping()
    public ResponseEntity<?> saveNewTeacher(@RequestBody TeacherDto teacherDto) {
        if (teacherDto.getId() == null) {
            if (userService.existsByEmail(teacherDto.getEmail())) {
                return ResponseEntity.badRequest().body("Пользователь с email " + teacherDto.getEmail() + " уже существует!");
            } else {
                teacherService.saveTeacherByDTO(teacherDto);
                return ResponseEntity.ok().body(teacherDto.getFirstName() + " " + teacherDto.getLastName() + " зарегистрирован!");
            }
        } else {
            if (userService.existsByEmail(teacherDto.getEmail())) {
                User emailUser = userService.getUserByEmail(teacherDto.getEmail());
                if (emailUser.getId() == teacherDto.getId()) {
                    teacherService.saveTeacherByDTO(teacherDto);
                    return ResponseEntity.ok().body("Данные учителя изменены!");
                } else {
                    return ResponseEntity.badRequest().body("Пользователь с email " +
                                                                teacherDto.getEmail() + " уже существует!");
                }
            } else {
                teacherService.saveTeacherByDTO(teacherDto);
                return ResponseEntity.ok().body("Данные учителя изменены!");
            }
        }
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<?> getTeacherById(@PathVariable(name = "teacherId") Long teacherId) {
        if (teacherService.existTeacherById(teacherId)) {
            return ResponseEntity.ok(teacherService.getTeacherDTOById(teacherId));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
