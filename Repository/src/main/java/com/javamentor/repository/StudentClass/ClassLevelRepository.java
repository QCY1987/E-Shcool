package com.javamentor.repository.StudentClass;

import com.javamentor.model.student_class.ClassLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassLevelRepository extends JpaRepository<ClassLevel, Long>  {

    ClassLevel getClassLevelByLevel(Integer level);
}
