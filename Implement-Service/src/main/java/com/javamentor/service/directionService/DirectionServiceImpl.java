package com.javamentor.service.directionService;

import com.javamentor.dto.model.direction.DirectionDto;
import com.javamentor.model.direction.Direction;
import com.javamentor.repository.direction.DirectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectionServiceImpl implements DirectionService {
    private final DirectionRepository directionRepository;

    @Autowired
    public DirectionServiceImpl(DirectionRepository directionRepository) {
        this.directionRepository = directionRepository;
    }

    /**
     * метод получения всех предметов
     *
     * @return список всех предметов
     */
    @Override
    public List<Direction> getAllDirections() {
        return directionRepository.findAll();
    }

    /**
     * метод получения предмета по id
     *
     * @param id предмета
     * @return предмет
     */
    @Override
    public Direction getDirectionById(Long id) {
        return directionRepository.findById(id).orElse(null);
    }

    /**
     * проверка существования предмета по id
     *
     * @param id id предмета
     * @return true/false
     */

    @Override
    public boolean existDirectionById(Long id) {
        return directionRepository.existsById(id);
    }

    /**
     * метод сохранения предмета
     *
     * @param directionDto объект с параметром для создания предмета. содержит поле с названием
     */
    @Override
    public void saveDirectionByDto(DirectionDto directionDto) {
        directionRepository
                .save(new Direction(
                        directionDto.getId(),
                        directionDto.getName()));

    }

    /**
     * проверка существования предмета по имени
     *
     * @param nameOfDirection - имя предмета
     * @return true/false
     */
    @Override
    public boolean existDirectionByName(String nameOfDirection) {
        return directionRepository.existsByName(nameOfDirection);
    }
}
