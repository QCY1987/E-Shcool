package com.javamentor.model.user;

import com.javamentor.dto.model.PostUserDto;
import com.javamentor.dto.model.teacher.TeacherDto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Set;

@Entity
@DiscriminatorValue("TEACHER")
public class Teacher extends User {

    public Teacher() {
    }

    public Teacher(String email, String firstName, String lastName, String middleName) {
        super(email, firstName, lastName, middleName);
    }

    public Teacher(Long id, String email, String firstName, String lastName, String middleName) {
        super(id, email, firstName, lastName, middleName);
    }

    public Teacher(Long id, String email, String firstName, String lastName, String middleName, Boolean isCurator) {
        super(id, email, firstName, lastName, middleName, isCurator);
    }

    public Teacher(
            String email,
            String firstName,
            String lastName,
            String middleName,
            String password,
            Set<Role> roles,
            Boolean enabled,
            LocalDate registrationDate) {
        super(email, firstName, lastName, middleName, password, roles, enabled, registrationDate);
    }

    public Teacher(TeacherDto teacherDto, String password, Set<Role> roles) {
        this(teacherDto.getEmail(),
                teacherDto.getFirstName(),
                teacherDto.getLastName(),
                teacherDto.getMiddleName(),
                password,
                roles,
                teacherDto.getEnabled(),
                teacherDto.getRegistrationDate());
    }
}
