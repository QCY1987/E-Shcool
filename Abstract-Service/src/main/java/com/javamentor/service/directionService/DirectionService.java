package com.javamentor.service.directionService;

import com.javamentor.dto.model.direction.DirectionDto;
import com.javamentor.model.direction.Direction;

import java.util.List;

public interface DirectionService {
    List<Direction> getAllDirections();

    Direction getDirectionById(Long id);

    boolean existDirectionById(Long id);

    void saveDirectionByDto (DirectionDto directionDto);

    boolean existDirectionByName(String nameOfDirection);
}
