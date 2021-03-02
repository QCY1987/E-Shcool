package com.javamentor.repository.direction;

import com.javamentor.model.direction.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectionRepository extends JpaRepository<Direction, Long> {
    Direction getDirectionById(Long directionId);

    Boolean existsByName(String name);
}
