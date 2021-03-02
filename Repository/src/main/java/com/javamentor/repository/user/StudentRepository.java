package com.javamentor.repository.user;

import com.javamentor.dto.model.student.StudentDto;
import com.javamentor.dto.model.UserDto;
import com.javamentor.model.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {
//    @Query("SELECT new com.javamentor.dto.model.UserDto(s.id,s.firstName,s.lastName,s.email,s.registrationDate) FROM Student s")
//    List<UserDto> findAllStudentsDTO();
    @Query(
            "SELECT new com.javamentor.dto.model.student.StudentDto(" +
                    "s.id, " +
                    "s.firstName, " +
                    "s.lastName, " +
                    "s.middleName, " +
                    "s.birthday, " +
                    "s.email, " +
                    "s.enabled, " +
                    "s.registrationDate, " +
                    "s.enrollmentDate, " +
                    "concat(s.studentClass.classLevel.level, s.studentClass.symbolClass.symbol) ) " +
                    "FROM Student s")
    List<StudentDto> findAllStudentsDTO();

    @Query(
            "SELECT new com.javamentor.dto.model.student.StudentDto(" +
                    "s.id, " +
                    "s.firstName, " +
                    "s.lastName, " +
                    "s.middleName, " +
                    "s.birthday, " +
                    "s.email, " +
                    "s.enabled, " +
                    "s.registrationDate, " +
                    "s.enrollmentDate, " +
                    "concat(s.studentClass.classLevel.level, s.studentClass.symbolClass.symbol)) " +
                    "FROM Student s " +
                    "WHERE s.lastName LIKE CONCAT('%', ?1 ,'%') " +
                    "OR lower(s.lastName) LIKE CONCAT('%', ?1 ,'%') " +
                    "OR s.firstName LIKE CONCAT('%', ?1 ,'%') " +
                    "OR lower(s.firstName) LIKE CONCAT('%', ?1 ,'%') " +
                    "OR s.middleName LIKE CONCAT('%', ?1 ,'%') " +
                    "OR lower(s.middleName) LIKE CONCAT('%', ?1 ,'%')")
    List<StudentDto> findAllStudentsDTOBySearch(String search);

    @Query("SELECT new com.javamentor.dto.model.student.StudentDto(" +
            "s.id, " +
            "s.firstName, " +
            "s.lastName, " +
            "s.middleName, " +
            "s.birthday, " +
            "s.email, " +
            "s.enabled, " +
            "s.registrationDate, " +
            "s.enrollmentDate, " +
            "concat(s.studentClass.classLevel.level, s.studentClass.symbolClass.symbol)) " +
            "FROM Student s WHERE " +
            "s.studentClass is not NULL")
    List<StudentDto> findAllStudentsDTOInClass();

    @Query("SELECT new com.javamentor.dto.model.student.StudentDto(" +
            "s.id, " +
            "s.firstName, " +
            "s.lastName, " +
            "s.middleName, " +
            "s.birthday, " +
            "s.email, " +
            "s.enabled, " +
            "s.registrationDate, " +
            "s.enrollmentDate, " +
            "concat(s.studentClass.classLevel.level, s.studentClass.symbolClass.symbol)) " +
            "FROM Student s WHERE " +
            "s.studentClass is NULL")
    List<StudentDto> findAllStudentsDTOOutClass();

    Student getStudentById(Long studentId);

    List<Student> getStudentsByStudentClassId(Long id);

    @Query(
            "SELECT new com.javamentor.dto.model.student.StudentDto(" +
                    "s.id, " +
                    "s.firstName, " +
                    "s.lastName, " +
                    "s.middleName, " +
                    "s.birthday, " +
                    "s.email, " +
                    "s.enabled, " +
                    "s.registrationDate, " +
                    "s.enrollmentDate, " +
                    "concat(s.studentClass.classLevel.level, s.studentClass.symbolClass.symbol) ) " +
                    "FROM Student s where s.studentClass.id = :id")
    List<StudentDto> getListStudentsDtoByStudentsClassId(Long id);

    boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.studentClass.id = null, s.isDeduction = true where s.id = :studentId and s.studentClass.id = :fromStudentClassId")
    void deductStudentFromClass(Long studentId, Long fromStudentClassId);

    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.studentClass.id = :toStudentClassId, s.isDeduction = false where s.id = :studentId")
    void recoverStudentToClass(Long studentId, Long toStudentClassId);

    @Query("SELECT COUNT(s.id) > 0 FROM Student s WHERE s.id = :studentId AND s.studentClass.id = :studentClassId")
    boolean isExistStudentInStudentClass(Long studentId, Long studentClassId);
}
