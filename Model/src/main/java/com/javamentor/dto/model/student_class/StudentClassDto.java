package com.javamentor.dto.model.student_class;

import com.javamentor.dto.model.student.StudentDto;
import com.javamentor.dto.model.teacher.TeacherDto;
import com.javamentor.model.student_class.ClassLevel;
import com.javamentor.model.student_class.SymbolClass;
import com.javamentor.model.user.Student;
import com.javamentor.model.user.Teacher;

import java.util.List;

public class StudentClassDto {

    private Long id;
    private ClassLevel classLevel;
    private SymbolClass symbolClass;
    private List<TeacherDto> curatorList;
    private List<StudentDto> studentList;

    public StudentClassDto() {
    }

    public StudentClassDto(
            Long id,
            ClassLevel classLevel,
            SymbolClass symbolClass
    ) {
        this.id = id;
        this.classLevel = classLevel;
        this.symbolClass = symbolClass;
    }

    public StudentClassDto(
            Long id,
            ClassLevel classLevel,
            SymbolClass symbolClass,
            List<TeacherDto> curatorList,
            List<StudentDto> studentList
    ) {
        this.id = id;
        this.classLevel = classLevel;
        this.symbolClass = symbolClass;
        this.curatorList = curatorList;
        this.studentList = studentList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClassLevel getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(ClassLevel classLevel) {
        this.classLevel = classLevel;
    }

    public SymbolClass getSymbolClass() {
        return symbolClass;
    }

    public void setSymbolClass(SymbolClass symbolClass) {
        this.symbolClass = symbolClass;
    }

    public List<TeacherDto> getCuratorList() {
        return curatorList;
    }

    public void setCuratorList(List<TeacherDto> curatorList) {
        this.curatorList = curatorList;
    }

    public List<StudentDto> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentDto> studentList) {
        this.studentList = studentList;
    }
}
