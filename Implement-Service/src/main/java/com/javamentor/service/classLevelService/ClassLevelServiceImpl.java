package com.javamentor.service.classLevelService;

import com.javamentor.model.student_class.ClassLevel;
import com.javamentor.repository.StudentClass.ClassLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassLevelServiceImpl implements ClassLevelService {

    private final ClassLevelRepository classLevelRepository;

    @Autowired
    public ClassLevelServiceImpl(ClassLevelRepository classLevelRepository) {
        this.classLevelRepository = classLevelRepository;
    }

    @Override
    public List<ClassLevel> getAllClassLevels() {
        return classLevelRepository.findAll();
    }

    @Override
    public ClassLevel getClassLevelById(Long id) {
        return classLevelRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existClassLevelById(Long id) {
        return classLevelRepository.existsById(id);
    }
    @Override
    public void save(ClassLevel classLevel) {
        classLevelRepository.save(classLevel);
    }
}
