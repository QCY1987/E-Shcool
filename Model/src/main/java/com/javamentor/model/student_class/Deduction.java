package com.javamentor.model.student_class;

import com.javamentor.model.user.Student;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "deductions")
public class Deduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reason")
    @NotBlank
    private String reason;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_class_id")
    private StudentClass fromStudentClass;

    @Column(name = "deduction_date")
    private LocalDate deductionDate;

    public Deduction() { }


    public Deduction(String reason, Student student, StudentClass fromStudentClass, LocalDate deductionDate) {
        this.reason = reason;
        this.student = student;
        this.fromStudentClass = fromStudentClass;
        this.deductionDate = deductionDate;
    }

    public Deduction(Student student, StudentClass fromStudentClass) {
        this.student = student;
        this.fromStudentClass = fromStudentClass;
    }


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getReason() { return reason; }

    public void setReason(String reason) { this.reason = reason; }

    public Student getStudent() { return student; }

    public void setStudent(Student student) { this.student = student; }

    public StudentClass getFromStudentClass() { return fromStudentClass; }

    public void setFromStudentClass(StudentClass fromStudentClass) { this.fromStudentClass = fromStudentClass; }

    public LocalDate getDeductionDate() { return deductionDate; }

    public void setDeductionDate(LocalDate deductionDate) { this.deductionDate = deductionDate; }

    @Override
    public String toString() {
        return "Студент " + student. getLastName() +
                " " + student.getFirstName() + " отчислен  из " +
                (fromStudentClass.getClassLevel().getLevel()) + " \"" +
                fromStudentClass.getSymbolClass().getSymbol() + "\"  класса!";

    }
}
