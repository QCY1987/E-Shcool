package com.javamentor.service.deductionService;

import com.javamentor.dto.model.deductionStudentDto.DeductionStudentDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DeductionService {

    List<DeductionStudentDto> findAllDeductionStudentsDto();
}
