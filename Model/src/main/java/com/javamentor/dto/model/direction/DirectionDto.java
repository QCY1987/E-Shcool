package com.javamentor.dto.model.direction;

public class DirectionDto {

    Long id;
    String name;

    public DirectionDto() {
    }

    public DirectionDto(String name) {
        this.name = name;
    }

    public DirectionDto(Long id, String name) {
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
