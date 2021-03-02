package com.javamentor.model.student_class;

import com.javamentor.model.user.Teacher;

import javax.persistence.*;
import java.util.*;

/**
 * Учебный класс со студентами (например, 1А или 2Б).
 * каждый класс будет содержать студентов
 * и кураторов (из числа учителей)
 */
@Entity
@Table(name = "student_classes")
public class StudentClass {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_level_id")
    private ClassLevel classLevel;

    @ManyToOne
    @JoinColumn(name = "symbol_class")
    private SymbolClass symbolClass;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "student_classes_curators",
            joinColumns = @JoinColumn(name = "student_class_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private Set<Teacher> curators = new HashSet<>();

    public StudentClass() {
    }

    public StudentClass(Long id, ClassLevel classLevel, SymbolClass symbolClass, Set<Teacher> curators) {
        this.id = id;
        this.classLevel = classLevel;
        this.symbolClass = symbolClass;
        this.curators = curators;
    }

    public StudentClass(Long id, ClassLevel classLevel, SymbolClass symbolClass) {
        this.id = id;
        this.classLevel = classLevel;
        this.symbolClass = symbolClass;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClassLevel getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(ClassLevel classLevel) {
        this.classLevel = classLevel;
    }

    public SymbolClass getSymbolClass() {
        return symbolClass;
    }

    public void setSymbolClass(SymbolClass symbolClass) {
        this.symbolClass = symbolClass;
    }

    public Set<Teacher> getCurators() {
        return curators;
    }

    public void setCurators(Set<Teacher> curators) {
        this.curators = curators;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentClass)) return false;
        StudentClass that = (StudentClass) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return " "
                + classLevel.getId() +
                " \"" + symbolClass.getSymbol() + "\"";
    }

}
