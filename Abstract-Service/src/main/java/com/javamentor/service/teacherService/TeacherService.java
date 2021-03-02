package com.javamentor.service.teacherService;

import com.javamentor.dto.model.PostUserDto;
import com.javamentor.dto.model.teacher.TeacherDto;
import com.javamentor.dto.model.UserDto;
import com.javamentor.model.user.Teacher;

import java.util.List;

public interface TeacherService {

    void save(Teacher teacher);

    List<Teacher> getAllTeachers();

    Teacher getTeacherById(Long id);

    UserDto getTeacherDTOById(Long id);

    boolean existTeacherById(Long id);

    boolean saveTeacher(Teacher teacher);

    String saveTeacherByDTO(TeacherDto teacherDto);

    List<TeacherDto> getAllTeacherDto();

    List<TeacherDto> getAllTeacherDtoBySearch(String search);

    List<UserDto> getAllTeacherDtoIsCurator();

    List<UserDto> getAllTeacherDtoIsNotCurator();

    boolean existsTeacherByEmail(String email);
}
