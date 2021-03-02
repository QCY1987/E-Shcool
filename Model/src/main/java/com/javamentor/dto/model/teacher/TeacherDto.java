package com.javamentor.dto.model.teacher;

import java.time.LocalDate;
import java.util.List;

public class TeacherDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private Boolean enabled;
    private LocalDate registrationDate;
    private List<String> studentClassesName;

    public TeacherDto() {
    }

    public TeacherDto(String email, String firstName, String lastName, String middleName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public TeacherDto(String email, String firstName, String lastName, String middleName, List<String> studentClassesName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.studentClassesName = studentClassesName;
    }

    public TeacherDto(Long id, String email, String firstName, String lastName, String middleName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public TeacherDto(Long id, String firstName, String lastName, String middleName, String email, Boolean enabled, LocalDate registrationDate) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.enabled = enabled;
        this.registrationDate = registrationDate;
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

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<String> getStudentClassesName() {
        return studentClassesName;
    }

    public void setStudentClassesName(List<String> studentClassesName) {
        this.studentClassesName = studentClassesName;
    }
}
