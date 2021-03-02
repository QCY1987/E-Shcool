package com.javamentor.model.direction;

import com.javamentor.dto.model.direction.DirectionDto;

import javax.persistence.*;

@Entity
@Table(name = "directions")
public class Direction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    String name;

    public Direction() {
    }

    public Direction(String name) {
        this.name = name;
    }

    public Direction(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
