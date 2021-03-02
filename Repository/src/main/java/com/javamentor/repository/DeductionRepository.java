package com.javamentor.repository;

import com.javamentor.model.student_class.Deduction;
import com.javamentor.dto.model.deductionStudentDto.DeductionStudentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface DeductionRepository extends JpaRepository<Deduction, Long> {

    @Query("SELECT new com.javamentor.dto.model.deductionStudentDto.DeductionStudentDto(" +
            "d.id, " +
            "d.reason, " +
            "d.student, " +
            "d.fromStudentClass, " +
            "d.deductionDate) " +
            "from Deduction d")
    List<DeductionStudentDto> findAllDeductionStudentsDto();
}
