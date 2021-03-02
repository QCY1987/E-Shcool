package com.javamentor.model.student_class;

import com.javamentor.model.student_class.StudentClass;
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
import java.time.LocalDate;

@Entity
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reason")
    private String reason;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_class_id")
    private StudentClass fromClass;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_class_id")
    private StudentClass toClass;

    @Column(name = "transfer_date")
    private LocalDate transferDate;

    public Transfer() {
    }

    public Transfer(String reason,Student student, StudentClass fromClass, StudentClass toClass, LocalDate transferDate) {
        this.reason = reason;
        this.student = student;
        this.fromClass = fromClass;
        this.toClass = toClass;
        this.transferDate = transferDate;
    }
    public Transfer(Long id,Student student, StudentClass fromClass, StudentClass toClass) {
        this.student = student;
        this.fromClass = fromClass;
        this.toClass = toClass;
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

    public StudentClass getFromClass() {
        return fromClass;
    }

    public void setFromClass(StudentClass fromClass) {
        this.fromClass = fromClass;
    }

    public StudentClass getToClass() {
        return toClass;
    }

    public void setToClass(StudentClass toClass) {
        this.toClass = toClass;
    }

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
    }

    @Override
    public String toString() {
        return "Студент " + student.getLastName() + " "
                + student.getFirstName() + " из "
                + fromClass.getClassLevel().getLevel() + " "
                + fromClass.getSymbolClass() + " переведен в "
                + toClass.getClassLevel().getLevel()  + " "
                + toClass.getSymbolClass() + " класс!";
    }
}
