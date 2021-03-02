package com.javamentor.service.studentClassService;

import com.javamentor.model.student_class.ClassLevel;
import com.javamentor.model.student_class.Deduction;
import com.javamentor.model.student_class.Recovery;
import com.javamentor.model.student_class.StudentClass;
import com.javamentor.model.student_class.SymbolClass;
import com.javamentor.model.user.Student;
import com.javamentor.model.user.Teacher;
import com.javamentor.repository.DeductionRepository;
import com.javamentor.repository.RecoveryRepository;
import com.javamentor.repository.StudentClass.StudentClassRepository;
import com.javamentor.repository.StudentClass.TransferRepository;
import com.javamentor.repository.user.StudentRepository;
import com.javamentor.repository.user.TeacherRepository;
import com.javamentor.model.student_class.Deduction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class StudentClassServiceImplTest {

    @Mock
    StudentClassRepository studentClassRepository;

    @Mock
    StudentRepository studentRepository;

    @Mock
    TeacherRepository teacherRepository;

    @Mock
    DeductionRepository deductionRepository;

    @Mock
    RecoveryRepository recoveryRepository;

    @Mock
    TransferRepository transferRepository;

    @InjectMocks
    StudentClassServiceImpl studentClassService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        when(studentClassRepository.findAll()).thenReturn(studentClassList);
        when(studentClassRepository.findById(1L)).thenReturn(Optional.of(studentClassTest1));
        when(studentClassRepository.findById(2L)).thenReturn(Optional.of(studentClassTest2));
        when(studentClassRepository.existsById(studentClassTest1.getId())).thenReturn(true);
        when(studentClassRepository.getCuratorListIdsByStudentClassId(1L)).thenReturn(Collections.singletonList(1L));
        when(studentClassRepository.getCuratorListIds()).thenReturn(Collections.singletonList(1L));
        when(studentClassRepository.existStudentClassByLevelAndSymbol(classLevel, classA)).thenReturn(true);

        when(studentRepository.getStudentById(1L)).thenReturn(student1);
        when(studentRepository.getStudentById(2L)).thenReturn(student2);
        when(studentRepository.getStudentById(3L)).thenReturn(studentWithClass1);
        when(studentRepository.getStudentById(4L)).thenReturn(studentWithClass2);
        when(studentRepository.getStudentsByStudentClassId(1L)).thenReturn(Collections.EMPTY_LIST);

        when(teacherRepository.getTeacherById(1L)).thenReturn(teacher1);
    }

    private final ClassLevel classLevel = new ClassLevel(1L, 1);
    private final SymbolClass classA = new SymbolClass('A');
    private final SymbolClass classB = new SymbolClass('B');

    private final Teacher teacher1 = new Teacher(
            1L, "test1@mail.ru", "test1", "test1", "test1", true);
    private final Teacher teacher2 = new Teacher(
            2L, "test2@mail.ru", "test2", "test2", "test2");
    private final Set<Teacher> curators = new HashSet<>(Collections.singletonList(teacher1));

    private final StudentClass studentClassTest1 = new StudentClass(1L, classLevel, classA, curators);
    private final StudentClass studentClassTest2 = new StudentClass(2L, classLevel, classB, new HashSet<>());

    private final List<StudentClass> studentClassList = Collections.singletonList(studentClassTest1);

    private final Student student1 = new Student(
            1L, "test1@mail.ru", "test1", "test1", "test1");
    private final Student student2 = new Student(
            2L, "test2@mail.ru", "test2", "test2", "test2");
    private final Student studentWithClass1 = new Student(
            3L, "test3@mail.ru", "test3", "test3", "test3");
    private final Student studentWithClass2 = new Student(
            4L, "test4@mail.ru", "test4", "test4", "test4");
    private final List<Long> studentsIds = Arrays.asList(1L, 2L);
    private final List<Long> studentsIdsWithClass = Arrays.asList(3L, 4L);
    private final List<Long> curatorsIds = Collections.singletonList(1L);

    private final Deduction deductionTest = new Deduction("Прогул", student1, studentClassTest1, LocalDate.now());

    private final Recovery recoveryTest = new Recovery("Приказ 404", student1, studentClassTest1, LocalDate.now());

    @Test
    public void save() {
        studentClassService.save(studentClassTest1);
        verify(studentClassRepository, only()).save(studentClassTest1);
    }

    @Test
    public void deleteById() {
        studentClassService.save(studentClassTest1);
        studentClassService.deleteById(1L);
        verify(studentClassRepository, times(1)).deleteById(1L);
    }

    @Test
    public void getAllStudentClasses() {
        assertEquals(studentClassList, studentClassRepository.findAll());
        verify(studentClassRepository, only()).findAll();
    }

    @Test
    public void getStudentClassById() {
        assertEquals(Optional.of(studentClassTest1), studentClassRepository.findById(1L));
        verify(studentClassRepository, times(1)).findById(1L);
    }

    @Test
    public void existStudentClassById() {
        assertTrue(studentClassService.existStudentClassById(studentClassTest1.getId()));
        verify(studentClassRepository, times(1)).existsById(1L);
    }

    @Test
    public void addStudentToStudentClass() {
        studentClassService.addStudentToStudentClass(studentClassTest1.getId(), student1.getId());
        verify(studentClassRepository, only()).findById(1L);
        verify(studentRepository, times(1)).getStudentById(1L);
        assertEquals(student1, studentRepository.getStudentById(student1.getId()));
        verify(studentRepository, times(1)).save(student1);
    }

    @Test
    public void addStudentListToStudentClass() {
        studentClassService.addStudentListToStudentClass(studentClassTest1.getId(), studentsIds);
        verify(studentClassRepository, times(3)).findById(1L);
        verify(studentRepository, times(1)).save(student1);
        verify(studentRepository, times(1)).save(student2);
    }

    @Test
    public void deleteStudentFromStudentClass() {
        studentWithClass1.setStudentClass(studentClassTest1);
        studentClassService.deleteStudentFromStudentClass(studentClassTest1.getId(), studentWithClass1.getId());
        verify(studentClassRepository, only()).findById(1L);
        verify(studentRepository, times(1)).getStudentById(3L);
        verify(studentRepository, times(1)).save(studentWithClass1);
    }

    @Test
    public void getCuratorListIdsByStudentClassId() {
        List<Long> actualList = studentClassService.getCuratorListIdsByStudentClassId(studentClassTest1.getId());
        assertEquals(Collections.singletonList(1L), actualList);
        verify(studentClassRepository, only()).getCuratorListIdsByStudentClassId(studentClassTest1.getId());
    }

    @Test
    public void getCuratorListIds() {
        List<Long> expectedList = Collections.singletonList(1L);
        List<Long> actualList = studentClassService.getCuratorListIds();
        assertEquals(expectedList, actualList);
        verify(studentClassRepository, only()).getCuratorListIds();
    }

    @Test
    public void deleteStudentListFromStudentClass() {
        studentWithClass1.setStudentClass(studentClassTest1);
        studentWithClass2.setStudentClass(studentClassTest1);
        studentClassService.deleteStudentListFromStudentClass(studentClassTest1.getId(), studentsIdsWithClass);
        verify(studentClassRepository, times(3)).findById(1L);
        verify(studentRepository, times(1)).getStudentById(3L);
        verify(studentRepository, times(1)).getStudentById(4L);
        verify(studentRepository, times(1)).save(studentWithClass1);
        verify(studentRepository, times(1)).save(studentWithClass2);
    }

    @Test
    public void isCuratorInStudentClass() {
        boolean expected = studentClassService.isCuratorInStudentClass(studentClassTest1.getId(), teacher1.getId());
        assertTrue(expected);
        verify(studentClassRepository, only()).getCuratorListIdsByStudentClassId(studentClassTest1.getId());
    }

    @Test
    public void addCuratorToStudentClass() {
        studentClassService.addCuratorToStudentClass(studentClassTest2.getId(), teacher1.getId());
        verify(studentClassRepository, times(1)).findById(studentClassTest2.getId());
        verify(teacherRepository, only()).getTeacherById(teacher1.getId());
        verify(studentClassRepository, times(1))
                .getCuratorListIdsByStudentClassId(studentClassTest2.getId());
        verify(studentClassRepository, times(1)).save(studentClassTest2);
    }

    @Test
    public void isCuratorsInStudentClass() {
        boolean expected = studentClassService.isCuratorsInStudentClass(studentClassTest1.getId(), curatorsIds);
        assertTrue(expected);
        verify(studentClassRepository, only()).getCuratorListIdsByStudentClassId(studentClassTest1.getId());
    }

    @Test
    public void addCuratorListToStudentClass() {
        studentClassService.addCuratorListToStudentClass(studentClassTest2.getId(), curatorsIds);
        verify(studentClassRepository, times(1)).findById(studentClassTest2.getId());
        verify(teacherRepository, only()).getTeacherById(teacher1.getId());
        verify(studentClassRepository, times(1)).save(studentClassTest2);
    }

    @Test
    public void deleteCuratorFromStudentClass() {
        studentClassService.deleteCuratorFromStudentClass(studentClassTest1.getId(), teacher1.getId());
        verify(studentClassRepository, times(1)).findById(studentClassTest1.getId());
        verify(teacherRepository, times(1)).getTeacherById(teacher1.getId());
        verify(studentClassRepository, times(1)).save(studentClassTest1);
    }

    @Test
    public void deleteCuratorListFromStudentClass() {
        studentClassService.deleteCuratorListFromStudentClass(studentClassTest1.getId(), curatorsIds);
        verify(studentClassRepository, times(1)).findById(studentClassTest1.getId());
        verify(studentClassRepository, times(1)).save(studentClassTest1);
    }

    @Test
    public void isStudentClassEmpty() {
        assertTrue(studentClassService.isStudentClassEmpty(studentClassTest1.getId()));
        verify(studentRepository, only()).getStudentsByStudentClassId(studentClassTest1.getId());
    }

    @Test
    public void deleteStudentClass() {
        assertTrue(studentClassService.deleteStudentClass(studentClassTest1.getId()));
        verify(studentClassRepository, times(2)).findById(studentClassTest1.getId());
        verify(studentRepository, only()).getStudentsByStudentClassId(studentClassTest1.getId());
        verify(studentClassRepository, times(1))
                .getCuratorListIdsByStudentClassId(studentClassTest1.getId());
        verify(studentClassRepository, times(1)).save(studentClassTest1);
        verify(studentClassRepository, times(1)).deleteById(studentClassTest1.getId());
    }

    @Test
    public void existStudentClassByNumberAndSymbol() {
        assertTrue(studentClassService.existStudentClassByNumberAndSymbol(classLevel, classA));
        verify(studentClassRepository, only()).existStudentClassByLevelAndSymbol(classLevel, classA);
    }

    @Test
    public void deductionStudent() {
        studentClassService.deductStudent(deductionTest);
        verify(studentRepository, times(1))
                .deductStudentFromClass(deductionTest.getStudent().getId(), deductionTest.getFromStudentClass().getId());
        verify(deductionRepository, times(1)).save(deductionTest);
    }
    @Test
    public void recoveryStudent() {
        studentClassService.recoverStudent(recoveryTest);
        verify(studentRepository, times(1))
                .recoverStudentToClass(recoveryTest.getStudent().getId(), recoveryTest.getToStudentClass().getId());
        verify(recoveryRepository, times(1)).save(recoveryTest);
    }
}
