package com.javamentor.dto.model.student;

import com.javamentor.model.student_class.ClassLevel;
import com.javamentor.model.student_class.StudentClass;
import com.javamentor.model.user.User;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public class StudentDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private Boolean enabled;
    private LocalDate birthday;
    private LocalDate registrationDate;
    private String studentClass;
    private LocalDate enrollmentDate;
    private ClassLevel classLevel;
    private Boolean isDeduction;


    public StudentDto(
            Long id,
            String firstName,
            String lastName,
            String middleName,
            LocalDate birthday,
            String email,
            Boolean enabled,
            LocalDate registrationDate,
            LocalDate enrollmentDate,
            String studentClass) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.enabled = enabled;
        this.birthday = birthday;
        this.registrationDate = registrationDate;
        this.enrollmentDate = enrollmentDate;
        this.studentClass = studentClass;
    }

    public StudentDto(
            Long id,
            String firstName,
            String lastName,
            String middleName,
            LocalDate birthday,
            String email,
            Boolean enabled,
            LocalDate registrationDate,
            LocalDate enrollmentDate) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.enabled = enabled;
        this.birthday = birthday;
        this.registrationDate = registrationDate;
        this.enrollmentDate = enrollmentDate;
    }



//    public StudentDto(User user, LocalDate birthday) {
//        this(user.getId(), user.getFirstName(), user.getLastName(), user.getMiddleName(), birthday, user.getEmail(), user.getEnabled(), user.getRegistrationDate());
//    }

    public StudentDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public ClassLevel getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(ClassLevel classLevel) {
        this.classLevel = classLevel;
    }

    public Boolean getDeduction() { return isDeduction; }

    public void setDeduction(Boolean deduction) { isDeduction = deduction; }
}