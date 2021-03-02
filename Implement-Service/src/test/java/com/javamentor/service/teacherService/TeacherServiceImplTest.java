package com.javamentor.service.teacherService;

import com.javamentor.config.SimplePasswordGenerator;
import com.javamentor.dto.model.UserDto;
import com.javamentor.dto.model.teacher.TeacherDto;
import com.javamentor.model.user.Teacher;
import com.javamentor.repository.StudentClass.StudentClassRepository;
import com.javamentor.repository.user.RoleRepository;
import com.javamentor.repository.user.TeacherRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TeacherServiceImplTest {

    @Mock
    TeacherRepository repository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    SimplePasswordGenerator passwordGenerator;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Mock
    StudentClassRepository studentClassRepository;

    @InjectMocks
    private TeacherServiceImpl teacherService;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(passwordGenerator.randomPassword(10)).thenReturn("1234567890");
        when(repository.findById(teacher1.getId())).thenReturn(Optional.of(teacher1));
        when((repository.findById(-1L))).thenReturn(Optional.empty());
        when(repository.getTeacherById(1L)).thenReturn(teacher1);
        when(repository.existsById(teacher1.getId())).thenReturn(true);
        when(repository.existsById(-1L)).thenReturn(false);
        when(repository.existsByEmail(teacher1.getEmail())).thenReturn(true);
        when(repository.existsByEmail(teacher1.getEmail().concat("111"))).thenReturn(false);
        when(repository.findAll()).thenReturn(teacherList);
        when(repository.findAllTeachersDtoBySearch("test1")).thenReturn(teacherDto1List);
        when((repository.findAllTeachersDtoBySearch("test2"))).thenReturn(Collections.emptyList());
        when(repository.findAllTeachersIsCurator()).thenReturn(userDtoListIsCurator);
        when(repository.findAllTeacherIsNotCurator()).thenReturn(userDtoListIsNotCurator);
    }

    private final Teacher teacher1 = new Teacher(
            1L, "test1@mail.ru", "test1", "test1", "test1");
    private final Teacher teacher2 = new Teacher(
            2L, "test2@mail.ru", "test2", "test2", "test2");
    private final Teacher teacher3 = new Teacher(
            3L, "test3@mail.ru", "test3", "test3", "test3");
    private final List<Teacher> teacherList = Arrays.asList(teacher1, teacher2, teacher3);
    private final Teacher teacherNotId = new Teacher(
            "test4@mail.ru", "test4", "test4", "test4");
    private final TeacherDto teacherDto1 = new TeacherDto(
            teacher1.getId(), teacher1.getEmail(), teacher1.getFirstName(), teacher1.getLastName(), teacher1.getMiddleName());
    private final TeacherDto teacherDto2 = new TeacherDto(
            teacher2.getId(), teacher2.getEmail(), teacher2.getFirstName(), teacher2.getLastName(), teacher2.getMiddleName());
    private final TeacherDto teacherDto3 = new TeacherDto(
            teacher3.getId(), teacher3.getEmail(), teacher3.getFirstName(), teacher3.getLastName(), teacher3.getMiddleName());
    private final TeacherDto teacherDtoNotId = new TeacherDto(
            teacherNotId.getEmail(), teacherNotId.getFirstName(), teacherNotId.getLastName(), teacherNotId.getMiddleName());
    private final List<TeacherDto> teacherDtoList = Arrays.asList(teacherDto1, teacherDto2, teacherDto3);
    private final UserDto userDto = new UserDto(teacher1);

    private final List<String> studentClassesNameForTeacherDto1 = Arrays.asList("1A", "1B");
    private final List<String> studentClassesNameForTeacherDto2 = Arrays.asList("2A", "2B");
    private final List<String> studentClassesNameForTeacherDto3 = Arrays.asList("3A", "3B");

    private final List<TeacherDto> teacherDto1List = Arrays.asList(teacherDto1);

    private final Teacher teacherIsCurator = new Teacher(
            5L, "test5@mail.ru", "test5", "test5", "test5", true);
    private final Teacher teacherIsNotCurator = new Teacher(
            6L, "test6@mail.ru", "test6", "test6", "test6", false);

    private final UserDto userDtoIsCurator = new UserDto(teacherIsCurator.getId(), teacherIsCurator.getFirstName(),
            teacherIsCurator.getLastName(), teacherIsCurator.getEmail(), teacherIsCurator.getRegistrationDate());
    private final UserDto userDtoIsNotCurator = new UserDto(teacherIsNotCurator.getId(), teacherIsNotCurator.getFirstName(),
            teacherIsNotCurator.getLastName(), teacherIsNotCurator.getEmail(), teacherIsNotCurator.getRegistrationDate());

    private final List<UserDto> userDtoListIsCurator = Arrays.asList(userDtoIsCurator);
    private final List<UserDto> userDtoListIsNotCurator = Arrays.asList(userDtoIsNotCurator);

    @Test
    public void saveTeacherByDto() {
        teacherService.saveTeacherByDTO(teacherDtoNotId);
        verify(roleRepository, times(1)).findRoleByName("TEACHER");
        verify(passwordGenerator, times(1)).randomPassword(10);
        verify(passwordEncoder, times(1)).encode("1234567890");
        verify(repository, times(1)).save(teacherNotId);
    }

    @Test
    public void saveTeacher() {
        teacherService.saveTeacher(teacher1);
        verify(repository, only()).save(teacher1);
    }

    @Test
    public void updateTeacherByDTO() {
        teacherService.saveTeacherByDTO(teacherDto1);
        verify(repository, times(1)).getTeacherById(teacher1.getId());
        verify(repository, times(1)).save(teacher1);
    }

    @Test
    public void getAllTeachersDtoTest() {
        when(repository.findAllTeachersDto()).thenReturn(teacherDtoList);
        assertEquals(teacherDtoList, repository.findAllTeachersDto());
        verify(repository, only()).findAllTeachersDto();

        when(studentClassRepository.getStudentClassesNameByTeacherId(teacherDto1.getId()))
                .thenReturn(studentClassesNameForTeacherDto1);
        when(studentClassRepository.getStudentClassesNameByTeacherId(teacherDto2.getId()))
                .thenReturn(studentClassesNameForTeacherDto2);
        when(studentClassRepository.getStudentClassesNameByTeacherId(teacherDto3.getId()))
                .thenReturn(studentClassesNameForTeacherDto3);
        teacherDto1.setStudentClassesName(studentClassesNameForTeacherDto1);
        teacherDto2.setStudentClassesName(studentClassesNameForTeacherDto2);
        teacherDto3.setStudentClassesName(studentClassesNameForTeacherDto3);
        List<TeacherDto> teacherDtoListWithStudentClassesNames = Arrays.asList(teacherDto1, teacherDto2, teacherDto3);

        assertEquals(teacherDtoListWithStudentClassesNames, teacherService.getAllTeacherDto());
        verify(studentClassRepository, times(3)).getStudentClassesNameByTeacherId(any());
    }

    @Test
    public void getTeacherByRightId() {
        assertEquals(teacher1, teacherService.getTeacherById(teacher1.getId()));
        verify(repository, only()).findById(teacher1.getId());
    }

    @Test
    public void getTeacherByWrongId() {
        assertNull(teacherService.getTeacherById(-1L));
        verify(repository, only()).findById(-1L);
    }

    @Test
    public void getTeacherDtoByRightId() {
        assertEquals(userDto, teacherService.getTeacherDTOById(teacher1.getId()));
        verify(repository, times(1)).findById(1L);
    }

    @Test(expected = AssertionError.class)
    public void getTeacherDtoByWrongId() {
        assertNull(teacherService.getTeacherDTOById(-1L));
        verify(repository, times(1)).findById(-1L);
    }

    @Test
    public void existTeacherById() {
        assertTrue("true", teacherService.existTeacherById(1L));
        assertFalse("false", teacherService.existTeacherById(-1L));
        verify(repository, times(2)).existsById(any());
    }

    @Test
    public void existsTeacherByEmail() {
        assertTrue("true", teacherService.existsTeacherByEmail("test1@mail.ru"));
        assertFalse("false", teacherService.existsTeacherByEmail("test1@mail.ru111"));
        verify(repository, times(2)).existsByEmail(any());
    }

    @Test
    public void getAllTeachers() {
        assertEquals(teacherList, teacherService.getAllTeachers());
        verify(repository, only()).findAll();
    }

    @Test
    public void getAllTeacherDtoBySearch() {
        assertEquals(teacherDto1List, teacherService.getAllTeacherDtoBySearch("test1"));
        assertEquals(Collections.emptyList(), teacherService.getAllTeacherDtoBySearch("test2"));
        verify(repository, times(2)).findAllTeachersDtoBySearch(any());
    }

    @Test
    public void getAllTeacherDtoIsCurator() {
        assertEquals(userDtoListIsCurator, teacherService.getAllTeacherDtoIsCurator());
        verify(repository, only()).findAllTeachersIsCurator();
    }

    @Test
    public void getAllTeacherDtoIsNotCurator() {
        assertEquals(userDtoListIsNotCurator, teacherService.getAllTeacherDtoIsNotCurator());
        verify(repository, only()).findAllTeacherIsNotCurator();
    }
}