package com.javamentor.model.student_class;

import com.javamentor.model.user.Student;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "recoveries")
public class Recovery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reason")
    @NotBlank
    private String reason;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "to_student_class_id")
    private StudentClass toStudentClass;

    @Column(name = "recovery_date")
    private LocalDate recoveryDate;

    public Recovery() {
    }

    public Recovery(String reason, Student student, StudentClass toStudentClass, LocalDate recoveryDate) {
        this.reason = reason;
        this.student = student;
        this.toStudentClass = toStudentClass;
        this.recoveryDate = recoveryDate;
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

    public StudentClass getToStudentClass() {
        return toStudentClass;
    }

    public void setToStudentClass(StudentClass toStudentClass) {
        this.toStudentClass = toStudentClass;
    }

    public LocalDate getRecoveryDate() {
        return recoveryDate;
    }

    public void setRecoveryDate(LocalDate recoveryDate) {
        this.recoveryDate = recoveryDate;
    }

}
