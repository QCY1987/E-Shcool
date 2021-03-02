package com.javamentor.model.user;

import com.javamentor.dto.model.PostUserDto;

import javax.persistence.*;


import com.javamentor.model.student_class.ClassLevel;
import com.javamentor.model.student_class.StudentClass;

import java.time.LocalDate;
import java.util.Set;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {

    @ManyToOne
    @JoinColumn(name = "student_class")
    private StudentClass studentClass;

    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;

    @Column(name = "is_deduction")
    private Boolean isDeduction = false;

    @Column(name = "birthday")
    private LocalDate birthday;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_level_id")
    private ClassLevel classLevel;

    public Student() {

    }

    public Student(String email, String firstName, String lastName, String middleName, String password, Set<Role> roles, Boolean enabled, LocalDate registrationDate, LocalDate birthday, LocalDate enrollmentDate, ClassLevel classLevel) {
        super(email, firstName, lastName, middleName, password, roles, enabled, registrationDate);
        this.birthday = birthday;
        this.enrollmentDate = enrollmentDate;
        this.classLevel = classLevel;
    }
    public Student(String email, String firstName, String lastName, String middleName, String password, Set<Role> roles, Boolean enabled, LocalDate registrationDate, LocalDate birthday, LocalDate enrollmentDate, ClassLevel classLevel,Boolean isDeduction) {
        super(email, firstName, lastName, middleName, password, roles, enabled, registrationDate);
        this.birthday = birthday;
        this.enrollmentDate = enrollmentDate;
        this.classLevel = classLevel;
        this.isDeduction = isDeduction;
    }

    public Student(Long id, String email, String firstName, String lastName,String middleName) {
        super(id, email, firstName, lastName,middleName);
    }

    public Student(Long id, String email, String firstName, String lastName,String middleName, StudentClass studentClass) {
        super(id, email, firstName, lastName,middleName);
        this.studentClass = studentClass;
    }
    public Student(Long id, String email, String firstName, String lastName,String middleName, StudentClass studentClass, Boolean isDeduction) {
        super(id, email, firstName, lastName,middleName);
        this.studentClass = studentClass;
        this.isDeduction = isDeduction;
    }

    public Student(String email, String firstName, String lastName, String middleName, String password, Set<Role> roles, Boolean enabled, LocalDate registrationDate, LocalDate birthday, LocalDate enrollmentDate) {
        super(email, firstName, lastName, middleName, password, roles, enabled, registrationDate);
        this.birthday = birthday;
        this.enrollmentDate = enrollmentDate;
    }

    public StudentClass getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(StudentClass studentClass) {
        this.studentClass = studentClass;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public ClassLevel getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(ClassLevel classLevel) {
        this.classLevel = classLevel;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public Boolean getDeduction() { return isDeduction; }

    public void setDeduction(Boolean deduction) { isDeduction = deduction; }

    public Student(PostUserDto postUserDto, String password, Set<Role> roles) {
        this(postUserDto.getEmail(),
                postUserDto.getFirstName(),
                postUserDto.getLastName(),
                postUserDto.getMiddleName(),
                password,
                roles,
                postUserDto.getEnabled(),
                postUserDto.getRegistrationDate(),
                postUserDto.getBirthday(),
                postUserDto.getEnrollmentDate(),
                postUserDto.getClassLevel());
    }
}
