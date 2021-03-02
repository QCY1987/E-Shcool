package com.javamentor.service.lessonService;

import com.javamentor.dto.model.direction.PostLessonDto;
import com.javamentor.model.direction.Direction;
import com.javamentor.model.direction.Lesson;
import com.javamentor.model.student_class.ClassLevel;
import com.javamentor.model.student_class.StudentClass;
import com.javamentor.model.student_class.SymbolClass;
import com.javamentor.model.user.Teacher;
import com.javamentor.repository.direction.DirectionRepository;
import com.javamentor.repository.lesson.LessonRepository;
import com.javamentor.repository.user.TeacherRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LessonServiceImplTest {
    @InjectMocks
    private LessonServiceImpl lessonServiceImpl;

    @Mock
    LessonRepository lessonRepository;

    @Mock
    DirectionRepository directionRepository;

    @Mock
    TeacherRepository teacherRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(lessonRepository.findAll()).thenReturn(allLessons);
        when(lessonRepository.findById(lesson1.getId())).thenReturn(Optional.of(lesson1));
        when(lessonRepository.findById(-1L)).thenReturn(Optional.empty());
        when(lessonRepository.existsById(lesson1.getId())).thenReturn(true);
        when(lessonRepository.existsById(-1L)).thenReturn(false);
        when(directionRepository.getDirectionById(direction1.getId())).thenReturn(direction1);
        when(teacherRepository.getTeacherById(teacher1.getId())).thenReturn(teacher1);
    }

    private final Direction direction1 = new Direction(1L, "музыка");
    private final Direction direction2 = new Direction(2L, "химия");
    private final Direction direction3 = new Direction(3L, "физика");

    private final Teacher teacher1 = new Teacher(
            1L, "test1@mail.ru", "test1", "test1", "test1");
    private final Teacher teacher2 = new Teacher(
            2L, "test2@mail.ru", "test2", "test2", "test2");
    private final Teacher teacher3 = new Teacher(
            3L, "test3@mail.ru", "test3", "test3", "test3");

    private final ClassLevel classLevel = new ClassLevel(1L, 1);
    private final SymbolClass classA = new SymbolClass('А');
    private final SymbolClass classB = new SymbolClass('Б');
    private final SymbolClass classV = new SymbolClass('В');

    private final Set<Teacher> curators1 = new HashSet<>(Collections.singletonList(teacher1));
    private final Set<Teacher> curators2 = new HashSet<>(Collections.singletonList(teacher2));
    private final Set<Teacher> curators3 = new HashSet<>(Collections.singletonList(teacher3));

    private final StudentClass studentClass1 = new StudentClass(1L, classLevel, classA, curators1);
    private final StudentClass studentClass2 = new StudentClass(2L, classLevel, classB, curators2);
    private final StudentClass studentClass3 = new StudentClass(3L, classLevel, classV, curators3);

    private final Set<StudentClass> studentClassSet1AClass = new HashSet<>(Collections.singleton(studentClass1));
    private final Set<StudentClass> studentClassSet1BClass = new HashSet<>(Collections.singleton(studentClass2));
    private final Set<StudentClass> studentClassSet1VClass = new HashSet<>(Collections.singleton(studentClass3));

    private final LocalDateTime startLesson = LocalDateTime.MIN;
    private final LocalDateTime endLesson = LocalDateTime.MAX;

    private final Lesson lesson1 = new Lesson(1L, direction1, teacher1, studentClassSet1AClass, startLesson, endLesson);
    private final Lesson lesson2 = new Lesson(2L, direction2, teacher2, studentClassSet1BClass, startLesson, endLesson);
    private final Lesson lesson3 = new Lesson(3L, direction3, teacher3, studentClassSet1VClass, startLesson, endLesson);

    private final List<Lesson> allLessons = Arrays.asList(lesson1, lesson2, lesson3);

    private final PostLessonDto postLessonDto = new PostLessonDto(1L, 1L, startLesson, endLesson);

    @Test
    public void testGetAllLessons() {
        assertEquals(allLessons, lessonServiceImpl.getAllLessons());
        verify(lessonRepository, only()).findAll();
    }

    @Test
    public void testGetLessonById() {
        assertEquals(lesson1, lessonServiceImpl.getLessonById(lesson1.getId()));
        assertNull(lessonServiceImpl.getLessonById(-1L));
        verify(lessonRepository, times(2)).findById(any());
        verify(lessonRepository, times(1)).findById(lesson1.getId());
        verify(lessonRepository, times(1)).findById(-1L);
    }

    @Test
    public void testExistLessonById() {
        assertTrue("true", lessonServiceImpl.existLessonById(lesson1.getId()));
        assertFalse("false", lessonServiceImpl.existLessonById(-1L));
        verify(lessonRepository, times(2)).existsById(any());
    }

    @Test
    public void testSaveLessonByDTO() {
        lessonServiceImpl.saveLessonByDTO(postLessonDto);
        Lesson lesson1FromDto = new Lesson(direction1, teacher1, startLesson, endLesson);
        verify(directionRepository, times(1)).getDirectionById(direction1.getId());
        verify(teacherRepository, times(1)).getTeacherById(teacher1.getId());
        verify(lessonRepository, times(1)).save(lesson1FromDto);
    }
}
