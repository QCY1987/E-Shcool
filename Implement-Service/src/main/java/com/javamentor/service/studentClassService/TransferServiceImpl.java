package com.javamentor.service.studentClassService;

import com.javamentor.model.student_class.Transfer;
import com.javamentor.repository.StudentClass.TransferRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;

    public TransferServiceImpl(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    /**
     * метод перевода студента из класса в класс
     *
     * @param transfer -  трансфер
     */

    @Override
    public void transferStudent(Transfer transfer) {
        transfer.setTransferDate(LocalDate.now());
        transferRepository.updateStudentClass(transfer.getStudent().getId(), transfer.getToClass().getId());
        transferRepository.save(transfer);
    }
}
