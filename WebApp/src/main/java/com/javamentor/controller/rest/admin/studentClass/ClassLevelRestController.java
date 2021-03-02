package com.javamentor.controller.rest.admin.studentClass;


import com.javamentor.service.classLevelService.ClassLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/classLevel")
public class ClassLevelRestController {


    private final ClassLevelService classLevelService;

    @Autowired
    public ClassLevelRestController(ClassLevelService classLevelService) {
        this.classLevelService = classLevelService;
    }

    @GetMapping
    public ResponseEntity<?> getClassLevelList() {
        return ResponseEntity.ok(classLevelService.getAllClassLevels());
    }

    @GetMapping("/{classLevelId}")
    public ResponseEntity<?> getClassLevelById(@PathVariable(name = "classLevelId") Long classLevelId) {
        if (classLevelService.existClassLevelById(classLevelId)) {
            return ResponseEntity.ok(classLevelService.getClassLevelById(classLevelId));
        }
        return new ResponseEntity<>("Запрашиваемый контент не найден", HttpStatus.NO_CONTENT);
    }


}
