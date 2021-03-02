package com.javamentor.service.classLevelService;

import com.javamentor.model.student_class.ClassLevel;

import java.util.List;

public interface ClassLevelService {

    List<ClassLevel> getAllClassLevels();

    ClassLevel getClassLevelById(Long id);

    boolean existClassLevelById(Long id);

    void save(ClassLevel classLevel);

}
