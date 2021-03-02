package com.javamentor.repository.user;

import com.javamentor.dto.model.teacher.TeacherDto;
import com.javamentor.dto.model.UserDto;
import com.javamentor.model.user.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("SELECT " +
            "new com.javamentor.dto.model.teacher.TeacherDto(" +
            "t.id,t.firstName,t.lastName,t.middleName,t.email,t.enabled,t.registrationDate) " +
            "FROM Teacher t")
    List<TeacherDto> findAllTeachersDto();

    @Query("SELECT " +
            "new com.javamentor.dto.model.teacher.TeacherDto(" +
            "t.id, " +
            "t.firstName, " +
            "t.lastName, " +
            "t.middleName, " +
            "t.email, " +
            "t.enabled, " +
            "t.registrationDate ) " +
            "FROM Teacher t WHERE " +
            "t.firstName LIKE CONCAT('%', ?1 ,'%') " +
            "OR lower(t.firstName) LIKE CONCAT('%', ?1 ,'%') " +
            "OR t.middleName LIKE CONCAT('%', ?1 ,'%') " +
            "OR lower(t.middleName) LIKE CONCAT('%', ?1 ,'%') " +
            "OR t.lastName LIKE CONCAT('%', ?1 ,'%') " +
            "OR lower(t.lastName) LIKE CONCAT('%', ?1 ,'%')")
    List<TeacherDto> findAllTeachersDtoBySearch(String search);

    @Query("SELECT new com.javamentor.dto.model.UserDto(t.id, t.firstName, t.lastName, t.email, t.registrationDate) " +
            "FROM Teacher t WHERE t.isCurator = true")
    List<UserDto> findAllTeachersIsCurator();

    @Query("SELECT new com.javamentor.dto.model.UserDto(t.id, t.firstName, t.lastName, t.email, t.registrationDate) " +
            "FROM Teacher t WHERE t.isCurator = false")
    List<UserDto> findAllTeacherIsNotCurator();

    @Query("SELECT t FROM Teacher t WHERE t.id = :id")
    Teacher getTeacherById(Long id);

    boolean existsByEmail(String email);
}
