package com.javamentor.service.studentClassService;

import com.javamentor.dto.model.student.StudentDto;
import com.javamentor.dto.model.student_class.StudentClassDto;
import com.javamentor.model.student_class.*;

import java.util.List;

public interface StudentClassService {

    void save(StudentClass studentClass);

    void deleteById(Long studentClassId);

    List<StudentClass> getAllStudentClasses();

    List<StudentClassDto> getListStudentsClassByCurator(Long teacherId);

    StudentClass getStudentClassById(Long id);

    boolean existStudentClassById(Long id);

    void addStudentToStudentClass(Long studentClassId, Long studentId);

    void addStudentListToStudentClass(Long studentClassId, List<Long> students);

    void deleteStudentFromStudentClass(Long studentClassId, Long studentId);

    List<Long> getCuratorListIds();

    void deleteStudentListFromStudentClass(Long studentClassId, List<Long> students);

    List<Long> getCuratorListIdsByStudentClassId(Long studentClassId);

    boolean isCuratorInStudentClass(Long studentClassId, Long curatorId);

    void addCuratorToStudentClass(Long studentClassId, Long curatorId);

    boolean isCuratorsInStudentClass(Long studentClassId, List<Long> curatorsIds);

    void addCuratorListToStudentClass(Long studentClassId, List<Long> curatorsId);

    void deleteCuratorFromStudentClass(Long studentClassId, Long curatorId);

    void deleteCuratorListFromStudentClass(Long studentClassId, List<Long> curatorsId);

    boolean isStudentClassEmpty(Long studentClassId);

    boolean deleteStudentClass(Long studentClassId);

    boolean existStudentClassByNumberAndSymbol(ClassLevel classLevel, SymbolClass checkSymbolClass);

    void deductStudent(Deduction deduction);

    void recoverStudent(Recovery recovery);
}
