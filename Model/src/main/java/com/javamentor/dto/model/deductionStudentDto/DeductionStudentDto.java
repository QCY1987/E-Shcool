package com.javamentor.dto.model.deductionStudentDto;

import com.javamentor.model.student_class.StudentClass;
import com.javamentor.model.user.Student;

import java.time.LocalDate;

public class DeductionStudentDto {

    private Long id;
    private String reason;
    private Student student;
    private StudentClass fromStudentClass;
    private LocalDate deductionDate;

    public DeductionStudentDto(
            Long id,
            String reason,
            Student student,
            StudentClass fromStudentClass,
            LocalDate deductionDate) {
        this.id = id;
        this.reason = reason;
        this.student = student;
        this.fromStudentClass = fromStudentClass;
        this.deductionDate = deductionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudentClass getFromStudentClass() {
        return fromStudentClass;
    }

    public void setFromStudentClass(StudentClass fromStudentClass) {
        this.fromStudentClass = fromStudentClass;
    }

    public LocalDate getDeductionDate() {
        return deductionDate;
    }

    public void setDeductionDate(LocalDate deductionDate) {
        this.deductionDate = deductionDate;
    }
}
