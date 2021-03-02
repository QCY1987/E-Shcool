package com.javamentor.controller.rest.admin.studentClass;

import com.javamentor.service.studentClassService.StudentClassService;
import com.javamentor.service.studentService.StudentService;
import com.javamentor.service.teacherService.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/admin/studentClass")
public class StudentClassRestController {

    private final StudentClassService studentClassService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private static final Logger log = Logger.getLogger(StudentClassRestController.class.getName());

    @Autowired
    public StudentClassRestController(StudentClassService studentClassService,
                                      StudentService studentService,
                                      TeacherService teacherService) {
        this.studentClassService = studentClassService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    /**
     * метод вывода всех классов
     *
     * @return возвращает все классы со всеми атрибутами (с кураторами, студентами и т.д.)
     */

    @GetMapping
    public ResponseEntity<?> getStudentClassList() {
        return ResponseEntity.ok(studentClassService.getAllStudentClasses());
    }

    /**
     * метод вывода конкретного класса по id
     *
     * @param studentClassId id класса
     * @return возвращает класс со всеми атрибутами
     */
    @GetMapping("/{studentClassId}")
    public ResponseEntity<?> getStudentClassById(@PathVariable(name = "studentClassId") Long studentClassId) {
        if (studentClassService.existStudentClassById(studentClassId)) {
            return ResponseEntity.ok(studentClassService.getStudentClassById(studentClassId));
        }
        return new ResponseEntity<>("Запрашиваемый контент не найден", HttpStatus.NO_CONTENT);
    }


    /**
     * метод вывода студентов в конкретном классе
     *
     * @param studentClassId id класса
     * @return возвращает студентов из класса
     */
    @GetMapping("/{studentClassId}/students")
    public ResponseEntity<?> getAllStudentsInStudentClassByStudentClassId(@PathVariable(name = "studentClassId") Long studentClassId) {
        if (studentClassService.existStudentClassById(studentClassId)) {
            return ResponseEntity.ok(studentService.getStudentDtoByStudentClassId(studentClassId));
        }
        return new ResponseEntity<>("Запрашиваемый контент не найден", HttpStatus.NO_CONTENT);
    }

    /**
     * метод зачисления одного студента в класс
     *
     * @param studentClassId id класса
     * @param studentId      id зачисляемого студента
     * @return возвращает статус
     */

    @PostMapping("/{studentClassId}/student/{studentId}")
    public ResponseEntity<?> addStudentToStudentClassById(@PathVariable(name = "studentClassId") Long studentClassId,
                                                          @PathVariable(name = "studentId") Long studentId) {
        if (!studentClassService.existStudentClassById(studentClassId) || !studentService.existStudentById(studentId)) {
            log.info("Класс или студент не существует. проверьте веденные данные.");
            return new ResponseEntity<>("Класс или студент не существует. проверьте веденные данные.", HttpStatus.BAD_REQUEST);
        }
        studentClassService.addStudentToStudentClass(studentClassId, studentId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * метод зачисления нескольких студентов в класс по списку
     *
     * @param studentClassId id класса
     * @param studentsIds    список id студентов
     * @return возвращает статус
     */

    @PostMapping("/{studentClassId}/students/{studentsId}")
    public ResponseEntity<?> addStudentListToStudentClassById(@PathVariable(name = "studentClassId") Long studentClassId,
                                                              @PathVariable(name = "studentsId") List<Long> studentsIds) {
        if (!studentClassService.existStudentClassById(studentClassId) || studentsIds.isEmpty()) {
            log.info("класса не существует или список студентов для добавления в класс пустой.");
            return new ResponseEntity<>("класса не существует или список студентов для добавления в класс пустой.", HttpStatus.BAD_REQUEST);
        }
        studentClassService.addStudentListToStudentClass(studentClassId, studentsIds);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * метод удаления студента из класса
     *
     * @param studentClassId id класса
     * @param studentId      id зачисляемого студента
     * @return возвращает статус
     */
    @DeleteMapping("/{studentClassId}/student/{studentId}")
    public ResponseEntity<?> deleteStudentFromStudentClassById(@PathVariable(name = "studentClassId") Long studentClassId,
                                                               @PathVariable(name = "studentId") Long studentId) {

        if (!studentClassService.existStudentClassById(studentClassId) || !studentService.existStudentById(studentId)) {
            log.info("Класс или студент не существует. проверьте веденные данные.");
            return new ResponseEntity<>("Класс или студент не существует. проверьте веденные данные.", HttpStatus.BAD_REQUEST);
        }
        studentClassService.deleteStudentFromStudentClass(studentClassId, studentId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * метод удаления студентов из класса по списку
     *
     * @param studentClassId id класса
     * @param studentsIds    список id студентов
     * @return возвращает статус
     */
    @DeleteMapping("/{studentClassId}/students/{studentId}")
    public ResponseEntity<?> deleteStudentListFromStudentClassById(@PathVariable(name = "studentClassId") Long studentClassId,
                                                                   @PathVariable(name = "studentId") List<Long> studentsIds) {

        if (!studentClassService.existStudentClassById(studentClassId)) {
            log.info("Класса с Id " + studentClassId + " не существует");
            return new ResponseEntity<>("Класса с Id " + studentClassId + " не существует", HttpStatus.NO_CONTENT);
        }
        studentClassService.deleteStudentListFromStudentClass(studentClassId, studentsIds);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * метод вывода всех кураторов класса
     *
     * @param studentClassId id класса
     * @return возвращает кураторов класса
     */
    @GetMapping("/{studentClassId}/curators")
    public ResponseEntity<?> getAllCuratorsInStudentClassByStudentClassId(@PathVariable(name = "studentClassId") Long studentClassId) {
        if (studentClassService.existStudentClassById(studentClassId)) {
            return ResponseEntity.ok(studentClassService.getStudentClassById(studentClassId).getCurators());
        }
        return new ResponseEntity<>("Запрашиваемый контент не найден", HttpStatus.NO_CONTENT);
    }


    /**
     * метод назначения куратора в класс
     *
     * @param studentClassId id класса
     * @param curatorId      id куратора
     * @return возвращает статус
     */
    @PostMapping("/{studentClassId}/curator/{curatorId}")
    public ResponseEntity<?> addCuratorToStudentClassById(@PathVariable(name = "studentClassId") Long studentClassId,
                                                          @PathVariable(name = "curatorId") Long curatorId) {

        if (!studentClassService.existStudentClassById(studentClassId) || !teacherService.existTeacherById(curatorId)) {
            log.info("Преподавателя или класса не существует. проверьте введенные данные.");
            return new ResponseEntity<>("Преподавателя или класса не существует. проверьте введенные данные.", HttpStatus.BAD_REQUEST);
        }
        studentClassService.addCuratorToStudentClass(studentClassId, curatorId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * метод назначения нескольких кураторов в класс по списку
     *
     * @param studentClassId id класса
     * @param curatorsIds    список id кураторов
     * @return возвращает статус
     */
    @PostMapping("/{studentClassId}/curators/{curatorId}")
    public ResponseEntity<?> addCuratorListToStudentClassById(@PathVariable(name = "studentClassId") Long studentClassId,
                                                              @PathVariable(name = "curatorId") List<Long> curatorsIds) {

        if (!studentClassService.existStudentClassById(studentClassId) || curatorsIds.isEmpty()) {
            log.info("класс не существует или список кураторов для добавления в класс пустой.");
            return new ResponseEntity<>("класс не существует или список кураторов для добавления в класс пустой.", HttpStatus.BAD_REQUEST);
        }
        List<Long> curatorsInStudentClass = studentClassService.getCuratorListIdsByStudentClassId(studentClassId);
        curatorsIds.removeAll(curatorsInStudentClass);
        studentClassService.addCuratorListToStudentClass(studentClassId, curatorsIds);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * метод удаления куратора из класса
     *
     * @param studentClassId id класса
     * @param curatorId      id куратора
     * @return возвращает статус
     */

    @DeleteMapping("/{studentClassId}/curator/{curatorId}")
    public ResponseEntity<?> deleteCuratorFromStudentClassById(@PathVariable(name = "studentClassId") Long studentClassId,
                                                               @PathVariable(name = "curatorId") Long curatorId) {

        if (!studentClassService.existStudentClassById(studentClassId) || !teacherService.existTeacherById(curatorId)) {
            log.info("Класс или куратор не существует");
            return new ResponseEntity<>("Класс или куратор не существует", HttpStatus.BAD_REQUEST);
        }
        studentClassService.deleteCuratorFromStudentClass(studentClassId, curatorId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * метод удаления кураторов из класса по списку
     *
     * @param studentClassId id класса
     * @param curatorsIds    список id кураторов
     * @return возвращает статус
     */

    @DeleteMapping("/{studentClassId}/curators/{curatorId}")
    public ResponseEntity<?> deleteCuratorListFromStudentClassById(@PathVariable(name = "studentClassId") Long studentClassId,
                                                                   @PathVariable(name = "curatorId") List<Long> curatorsIds) {

        if (!studentClassService.existStudentClassById(studentClassId) || curatorsIds.isEmpty()) {
            log.info("класс не существует или список кураторов для удаления из класса пустой.");
            return new ResponseEntity<>("класс не существует или список кураторов для удаления из класса пустой.", HttpStatus.BAD_REQUEST);
        }
        List<Long> curatorsInStudentClass = studentClassService.getCuratorListIdsByStudentClassId(studentClassId);
        curatorsIds.retainAll(curatorsInStudentClass);
        studentClassService.deleteCuratorListFromStudentClass(studentClassId, curatorsIds);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * удаление класса
     * в классе не должно быть студентов
     * используется отдельный метод для проверки наличия студентов в классе
     * студенты удаляются отдельными методами.
     * кураторы из класса удаляются в этом методе перед удалением класса.
     *
     * @param studentClassId id класса
     * @return возвращает true/false
     */

    @DeleteMapping("/{studentClassId}")
    public ResponseEntity<?> deleteStudentClassById(@PathVariable(name = "studentClassId") Long studentClassId) {
        if (!studentClassService.existStudentClassById(studentClassId) || !studentClassService.isStudentClassEmpty(studentClassId)) {
            log.info("класс не существует или в классе есть студенты, и он не может быть удален.");
            return new ResponseEntity<>("класс не существует или в классе есть студенты, и он не может быть удален.", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(studentClassService.deleteStudentClass(studentClassId));
    }
}
