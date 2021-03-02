package com.javamentor.model.direction;

import com.javamentor.model.student_class.StudentClass;
import com.javamentor.model.user.Teacher;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "direction_id")
    private Direction direction;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "lessons_student_classes",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "student_class_id"))
    private Set<StudentClass> studentClass;

    @Column(name = "startLesson")
    private LocalDateTime startLesson;

    @Column(name = "endLesson")
    private LocalDateTime endLesson;

    public Lesson() {
    }

    public Lesson(Long id, Direction direction, Teacher teacher, Set<StudentClass> studentClass, LocalDateTime startLesson, LocalDateTime endLesson) {
        this.id = id;
        this.direction = direction;
        this.teacher = teacher;
        this.studentClass = studentClass;
        this.startLesson = startLesson;
        this.endLesson = endLesson;
    }

    public Lesson(Direction direction, Teacher teacher, LocalDateTime startLesson, LocalDateTime endLesson) {
        this.direction = direction;
        this.teacher = teacher;
        this.startLesson = startLesson;
        this.endLesson = endLesson;
    }

    public LocalDateTime getStartLesson() {
        return startLesson;
    }

    public Long getId() {
        return id;
    }

    public void setStartLesson(LocalDateTime startLesson) {
        this.startLesson = startLesson;
    }

    public LocalDateTime getEndLesson() {
        return endLesson;
    }

    public void setEndLesson(LocalDateTime endLesson) {
        this.endLesson = endLesson;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<StudentClass> getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(Set<StudentClass> studentClass) {
        this.studentClass = studentClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(id, lesson.id) && Objects.equals(direction, lesson.direction) && Objects.equals(teacher, lesson.teacher) && Objects.equals(studentClass, lesson.studentClass) && Objects.equals(startLesson, lesson.startLesson) && Objects.equals(endLesson, lesson.endLesson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, direction, teacher, studentClass, startLesson, endLesson);
    }
}
