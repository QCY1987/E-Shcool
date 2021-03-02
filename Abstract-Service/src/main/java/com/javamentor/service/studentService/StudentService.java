package com.javamentor.service.studentService;

import com.javamentor.dto.model.PostUserDto;
import com.javamentor.dto.model.student.StudentDto;
import com.javamentor.dto.model.UserDto;
import com.javamentor.model.user.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    void save(Student student);

    List<Student> getAllStudents();

    Student getStudentById(Long id);

    UserDto getStudentDTOById(Long id);

    boolean existStudentById(Long id);

    boolean existsStudentByEmail(String email);

    List<Student> getStudentsByStudentClassId(Long id);

    List<StudentDto> getStudentDtoByStudentClassId(Long id);

    boolean saveStudent(Student student);

    String saveStudentByDTO(PostUserDto postUserDto);

    List<StudentDto> getAllStudentDto();

    List<StudentDto> getAllStudentDtoBySearch(String search);
    List<StudentDto> getAllStudentDtoInClass();

    List<StudentDto> getAllStudentDtoOutClass();

    Optional<Student> findById(Long id);

    boolean isExistStudentInStudentClass(Long studentId, Long studentClassId);
}
