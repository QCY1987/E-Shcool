package com.javamentor.model.student_class;

import javax.persistence.*;

/**
 * набор параллелей классов или уровней. 1ые классы, 2ые классы и т.д.
 */

@Entity
@Table(name = "class_levels")
public class ClassLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_class", unique = true)
    private Integer level;

    public ClassLevel() {
    }

    public ClassLevel(Long id, Integer level) {
        this.id = id;
        this.level = level;
    }

    public ClassLevel(Integer level) {
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
