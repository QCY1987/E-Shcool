package com.javamentor.service.studentClassService;

import com.javamentor.model.student_class.ClassLevel;
import com.javamentor.model.student_class.StudentClass;
import com.javamentor.model.student_class.SymbolClass;
import com.javamentor.model.student_class.Transfer;
import com.javamentor.model.user.Student;
import com.javamentor.repository.StudentClass.StudentClassRepository;
import com.javamentor.repository.StudentClass.TransferRepository;
import com.javamentor.repository.user.StudentRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransferServiceImplTest {

    @Mock
    StudentRepository studentRepository;

    @Mock
    TransferRepository transferRepository;

    @Mock
    StudentClassRepository studentClassRepository;

    @InjectMocks
    private TransferServiceImpl transferService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(studentRepository.getStudentById(1L)).thenReturn(student1WithClass);

        when(studentClassRepository.findById(2L)).thenReturn(Optional.of(studentClass1));
        when(studentClassRepository.findById(3L)).thenReturn(Optional.of(studentClass2));

        when((studentRepository.findById(-1L))).thenReturn(Optional.empty());
        when((studentClassRepository.findById(-1L))).thenReturn(Optional.empty());
    }

    private final ClassLevel classLevel1 = new ClassLevel(8);
    private final StudentClass studentClass1 = new StudentClass(2L, classLevel1, new SymbolClass('А'));
    private final StudentClass studentClass2 = new StudentClass(3L, classLevel1, new SymbolClass('Б'));

    private final Student student1WithClass = new Student(1L,"user1@gmail.com","Вася","Иванов","user1MiddleName",studentClass1);
    private final Transfer transfer1 = new Transfer(1L, student1WithClass, studentClass1, studentClass2);

    @Test
    public void transferStudent() {
        transferService.transferStudent(transfer1);
        verify(transferRepository, times(1)).updateStudentClass(transfer1.getStudent().getId(), transfer1.getToClass().getId());
        verify(transferRepository, times(1)).save(transfer1);
    }
}
