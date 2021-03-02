package com.javamentor.controller.rest.admin.direction;

import com.javamentor.dto.model.direction.DirectionDto;
import com.javamentor.service.directionService.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/direction")
public class DirectionRestController {
    private final DirectionService directionService;

    @Autowired
    public DirectionRestController(DirectionService directionService) {
        this.directionService = directionService;
    }

    /**
     * метод вывода всех предметов
     *
     * @return перечень всех предметов
     */
    @GetMapping
    public ResponseEntity<?> getDirectionList() {
        return ResponseEntity.ok(directionService.getAllDirections());
    }

    /**
     * метод создания нового предмета
     *
     * @param directionDto - передается объект, содержащий название нового предмета
     * @return статус
     */
    @PostMapping
    public ResponseEntity<?> saveNewDirection(@RequestBody DirectionDto directionDto) {
        if (directionService.existDirectionByName(directionDto.getName())) {
            return ResponseEntity.badRequest()
                    .body("Предмет "+directionDto.getName()+", уже существует");
        } else {
            directionService.saveDirectionByDto(directionDto);
            return ResponseEntity.ok().body("Предмет " + directionDto.getName() + ", успешно сохранен");
        }

    }

    /**
     * метод вывода предмета по id
     *
     * @param directionId id предмета
     * @return статус
     */
    @GetMapping("/{directionId}")
    public ResponseEntity<?> getDirectionById(@PathVariable(name = "directionId") Long directionId) {
        if (directionService.existDirectionById(directionId)) {
            return ResponseEntity.ok(directionService.getDirectionById(directionId));
        }
        return new ResponseEntity<>("Запрашиваемый контент не найден", HttpStatus.NO_CONTENT);
    }
}