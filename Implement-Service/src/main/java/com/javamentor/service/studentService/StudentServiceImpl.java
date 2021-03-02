package com.javamentor.service.studentService;

import com.javamentor.config.SimplePasswordGenerator;
import com.javamentor.dto.model.PostUserDto;
import com.javamentor.dto.model.student.StudentDto;
import com.javamentor.dto.model.UserDto;
import com.javamentor.model.user.Role;
import com.javamentor.model.user.Student;
import com.javamentor.repository.user.RoleRepository;
import com.javamentor.repository.user.StudentRepository;
import com.javamentor.service.mailService.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.*;
import java.util.logging.Logger;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private static final Logger log = Logger.getLogger(Student.class.getName());

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private SimplePasswordGenerator passwordGenerator;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<StudentDto> getAllStudentDto() {
        return studentRepository.findAllStudentsDTO();
    }

    @Override
    public List<StudentDto> getAllStudentDtoInClass() {
        return studentRepository.findAllStudentsDTOInClass();
    }

    @Override
    public List<StudentDto> getAllStudentDtoOutClass() {
        return studentRepository.findAllStudentsDTOOutClass();
    }


    @Override
    public List<StudentDto> getAllStudentDtoBySearch(String search) {
        return studentRepository.findAllStudentsDTOBySearch(search);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public UserDto getStudentDTOById(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        assert student != null;
        return new UserDto(student);
    }

    @Override
    public boolean existStudentById(Long id) {
        return studentRepository.existsById(id);
    }


    @Override
    public List<Student> getStudentsByStudentClassId(Long id) {
        return studentRepository.getStudentsByStudentClassId(id);
    }

    @Override
    public List<StudentDto> getStudentDtoByStudentClassId(Long id) {
        return studentRepository.getListStudentsDtoByStudentsClassId(id);
    }

    @Override
    public boolean saveStudent(Student student) {
        studentRepository.save(student);
        return true;
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public boolean existsStudentByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }

    @Override
    public boolean isExistStudentInStudentClass(Long studentId, Long studentClassId) {
        return studentRepository.isExistStudentInStudentClass(studentId, studentClassId);
    }

    /**
     * Метод сохраняет нового студента, если его нет в базе, и редактирует
     * существующего, если в базе такой студент найден.
     * В качестве пароля для нового студента выступает метод генерации случайных символов.
     *
     * @param postUserDto - принимает с фронта данные о студенте
     * @return String - возвращает строку с информацией о выполненных действиях
     */
    @Override
    public String saveStudentByDTO(PostUserDto postUserDto) {
        try {
            if (postUserDto.getId() == null) {
                Set<Role> roles = Collections.singleton(roleRepository.findRoleByName("STUDENT"));
                String randomPass = passwordGenerator.randomPassword(10);
                Student student = new Student(postUserDto, passwordEncoder.encode(randomPass), roles);
                studentRepository.save(student);
                Map<String, Object> map = new HashMap<>();
                String message = "Ваш пароль: " + randomPass;
                map.put("message", message);
                mailService.sendToEmailUsingTemplate(postUserDto.getEmail(), "Успешная регистрация!",
                        map);
                log.info("Студент с id "
                        + student.getId() + " "
                        + student.getLastName() + " "
                        + student.getFirstName()
                        + " был добавлен.");

                return "Студент " + student.getFirstName() + " "
                        + student.getLastName() + " успешно добавлен.";

            } else {
                Student existingStudent = studentRepository.getStudentById(postUserDto.getId());
                existingStudent.setFirstName(postUserDto.getFirstName());
                existingStudent.setLastName(postUserDto.getLastName());
                existingStudent.setMiddleName(postUserDto.getMiddleName());
                existingStudent.setBirthday(postUserDto.getBirthday());
                existingStudent.setRegistrationDate(postUserDto.getRegistrationDate());
                existingStudent.setEnrollmentDate(postUserDto.getEnrollmentDate());
                existingStudent.setEnabled(postUserDto.getEnabled());
                existingStudent.setEmail(postUserDto.getEmail());
                studentRepository.save(existingStudent);
                log.info("Данные студента с id "
                        + existingStudent.getId() + " "
                        + existingStudent.getLastName() + " "
                        + existingStudent.getFirstName()
                        + " были отредактированы.");

                return "Студент " + existingStudent.getFirstName() + " "
                        + existingStudent.getLastName() + " успешно обновлен.";
            }
        } catch (DataAccessException | MessagingException dae) {
            return "Ошибка сохранения студента " + dae.getMessage();
        }
    }
}
