package com.javamentor.service.deductionService;

import com.javamentor.dto.model.deductionStudentDto.DeductionStudentDto;
import com.javamentor.model.student_class.ClassLevel;
import com.javamentor.model.student_class.StudentClass;
import com.javamentor.model.student_class.SymbolClass;
import com.javamentor.model.user.Student;
import com.javamentor.repository.DeductionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import com.javamentor.model.student_class.Deduction;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class DeductionServiceImplTest {

    @Mock
    DeductionRepository deductionRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        when(deductionRepository.findAllDeductionStudentsDto()).thenReturn(deductionStudentDtoList);
    }

    private final ClassLevel classLevel1 = new ClassLevel(1);
    private final ClassLevel classLevel2 = new ClassLevel(2);

    private final SymbolClass symbolClass1 = new SymbolClass('A');
    private final SymbolClass symbolClass2 = new SymbolClass('Б');

    private final StudentClass studentClass1 = new StudentClass(1L, classLevel1, symbolClass1);
    private final StudentClass studentClass2 = new StudentClass(2L, classLevel2, symbolClass2);

    private final Student student1 = new Student(1L, "test1@Email", "test1FirstName", "test1LastName", "test1MiddleName", studentClass1, true);
    private final Student student2 = new Student(2L, "test2@Email", "test2FirstName", "test2LastName", "test2MiddleName", studentClass2, true);

    private final Deduction deductionStudent1 = new Deduction("Некая причина", student1, studentClass1, LocalDate.now());
    private final Deduction deductionStudent2 = new Deduction("Некая причина", student2, studentClass2, LocalDate.now());

    private final DeductionStudentDto deductionStudentDto1 = new DeductionStudentDto(
            deductionStudent1.getId(), deductionStudent1.getReason(), deductionStudent1.getStudent(), deductionStudent1.getFromStudentClass(), deductionStudent1.getDeductionDate());
    private final DeductionStudentDto deductionStudentDto2 = new DeductionStudentDto(
            deductionStudent2.getId(), deductionStudent2.getReason(), deductionStudent2.getStudent(), deductionStudent2.getFromStudentClass(), deductionStudent2.getDeductionDate());

    private final List<DeductionStudentDto> deductionStudentDtoList = Arrays.asList(deductionStudentDto1, deductionStudentDto2);



    @Test
    public void findAllDeductionStudentsDto() {
        assertEquals(deductionStudentDtoList, deductionRepository.findAllDeductionStudentsDto());
        verify(deductionRepository, only()).findAllDeductionStudentsDto();
    }
}