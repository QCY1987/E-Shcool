package com.javamentor.controller.rest.admin.user;

import com.javamentor.dto.model.PostUserDto;
import com.javamentor.model.student_class.Deduction;
import com.javamentor.model.student_class.Recovery;
import com.javamentor.model.student_class.Transfer;
import com.javamentor.model.user.Student;
import com.javamentor.model.user.User;
import com.javamentor.service.adminService.UserService;
import com.javamentor.service.deductionService.DeductionService;
import com.javamentor.service.studentClassService.StudentClassService;
import com.javamentor.service.studentClassService.TransferService;
import com.javamentor.service.studentService.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/student")
public class StudentRestController {

    private final StudentService studentService;
    private final UserService userService;
    private final StudentClassService studentClassService;
    private final TransferService transferService;
    private final DeductionService deductionService;

    @Autowired
    public StudentRestController(StudentService studentService, UserService userService, StudentClassService studentClassService, TransferService transferService, DeductionService deductionService) {
        this.studentService = studentService;
        this.userService = userService;
        this.studentClassService = studentClassService;
        this.transferService = transferService;
        this.deductionService = deductionService;
    }

    /**
     * метод получения всех студентов
     *
     * @return список студентов
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllStudent() {
        return ResponseEntity.ok(studentService.getAllStudentDto());
    }

    /**
     * метод получения всех студентов, зачисленных в классы
     *
     * @return список зачисленных в классы студентов
     */
    @GetMapping("/all/in_class")
    public ResponseEntity<?> getAllStudentInClass() {
        return ResponseEntity.ok(studentService.getAllStudentDtoInClass());
    }

    /**
     * метод получения студентов, которые не зачислены в классы
     *
     * @return список незачисленных в классы студентов
     */
    @GetMapping("/all/out_class")
    public ResponseEntity<?> getAllStudentOutClass() {
        return ResponseEntity.ok(studentService.getAllStudentDtoOutClass());
    }

    /**
     * метод поиска студентов по фамилии и имени
     *
     * @param search фрагмент имени или фамилии
     * @return список студентов, если есть совпадения
     */
    @GetMapping("/all/{search}")
    public ResponseEntity<?> getStudentListBySearch(@PathVariable String search) {
        return ResponseEntity.ok(studentService.getAllStudentDtoBySearch(search));
    }

    @PostMapping
    public ResponseEntity<?> saveStudent(@RequestBody PostUserDto postUserDto) {
        if (postUserDto.getId() == null) {
            if (userService.existsByEmail(postUserDto.getEmail())) {
                return ResponseEntity.badRequest().body("Пользователь с email " + postUserDto.getEmail() + " уже существует!");
            } else {
                studentService.saveStudentByDTO(postUserDto);
                return new ResponseEntity<>("Ученик зарегистрирован!", HttpStatus.OK);
            }
        } else {
            if (userService.existsByEmail(postUserDto.getEmail())) {
                User emailUser = userService.getUserByEmail(postUserDto.getEmail());
                if (emailUser.getId() == postUserDto.getId()) {
                    studentService.saveStudentByDTO(postUserDto);
                    return ResponseEntity.ok().body("Данные ученика изменены!");
                } else {
                    return ResponseEntity.badRequest().body("Пользователь с email " +
                            postUserDto.getEmail() + " уже существует!");
                }
            } else {
                studentService.saveStudentByDTO(postUserDto);
                return ResponseEntity.ok().body("Данные ученика изменены!");
            }
        }
    }

    /**
     * метод вывода определенного студента по его id
     *
     * @param studentId id студента
     * @return студент
     */
    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudentById(@PathVariable(name = "studentId") Long studentId) {
        if (studentService.existStudentById(studentId)) {
            return ResponseEntity.ok(studentService.getStudentById(studentId));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferStudent(@RequestBody Transfer transfer) {
        if (transfer.getFromClass().equals(transfer.getStudent().getStudentClass())) {
            transferService.transferStudent(transfer);
            return new ResponseEntity<>("Ученик переведен!", HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().body("Ученик не принадлежит данному классу.");
        }
    }

    @PostMapping("/deduction")
    public ResponseEntity<?> deductionStudent(@RequestBody Deduction deduction) {
        boolean isExist = studentService.isExistStudentInStudentClass(deduction.getStudent().getId(),
                deduction.getFromStudentClass().getId());
        if (isExist) {
            studentClassService.deductStudent(deduction);
            return new ResponseEntity<>("Ученик отчислен!", HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().body("Ученик не принадлежит данному классу.");
        }
    }

    @PostMapping("/recovery")
    public ResponseEntity<?> recoveryStudent(@RequestBody Recovery recovery) {
        Optional<Student> student = studentService.findById(recovery.getStudent().getId());
        if (!student.isPresent()) {
            return ResponseEntity.badRequest().body("Ученика с таким id не существует");
        } else if (!student.get().getDeduction()){
            return ResponseEntity.badRequest().body("Ученик не отчислен");
        } else if (!studentClassService.existStudentClassById(recovery.getToStudentClass().getId())) {
            return ResponseEntity.badRequest().body("Класса не существует");
        }
        studentClassService.recoverStudent(recovery);
        return new ResponseEntity<>("Ученик восстановлен", HttpStatus.OK);
    }

    @GetMapping("/deduction/all")
    public ResponseEntity<?> getAllDeductionStudents() {
        return ResponseEntity.ok(deductionService.findAllDeductionStudentsDto());
    }
}
