package com.javamentor.repository.StudentClass;

import com.javamentor.dto.model.student_class.StudentClassDto;
import com.javamentor.model.student_class.ClassLevel;
import com.javamentor.model.student_class.StudentClass;
import com.javamentor.model.student_class.SymbolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {

    @Query("SELECT sc.id FROM StudentClass sc JOIN sc.curators c where sc.id = :studentClassId")
    List<Long> getCuratorListIdsByStudentClassId(Long studentClassId);

    @Query("SELECT count(sc.id) > 0 from StudentClass sc where sc.classLevel = :classLevel  and sc.symbolClass = :checkSymbolClass")
    boolean existStudentClassByLevelAndSymbol(ClassLevel classLevel, SymbolClass checkSymbolClass);

    @Query("select distinct c.id from StudentClass sc join sc.curators c")
    List<Long> getCuratorListIds();

    @Query("SELECT concat(sc.classLevel.level, sc.symbolClass.symbol) FROM StudentClass sc JOIN sc.curators c WHERE c.id = :teacherId")
    List<String> getStudentClassesNameByTeacherId(Long teacherId);

    @Query("SELECT  new com.javamentor.dto.model.student_class.StudentClassDto(" +
            "sc.id, sc.classLevel, sc.symbolClass)" +
            "FROM StudentClass sc JOIN sc.curators c WHERE c.id = :teacherId")
    List<StudentClassDto> getListStudentsClassByCurator(Long teacherId);

}
