package com.javamentor.service.deductionService;

import com.javamentor.dto.model.deductionStudentDto.DeductionStudentDto;
import com.javamentor.repository.DeductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeductionServiceImpl implements DeductionService{

    private final DeductionRepository deductionRepository;

    @Autowired
    public DeductionServiceImpl(DeductionRepository deductionRepository) {
        this.deductionRepository = deductionRepository;
    }

    @Override
    public List<DeductionStudentDto> findAllDeductionStudentsDto() {
        return this.deductionRepository.findAllDeductionStudentsDto();
    }
}
