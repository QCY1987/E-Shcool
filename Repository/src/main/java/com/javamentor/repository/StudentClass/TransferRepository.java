package com.javamentor.repository.StudentClass;

import com.javamentor.model.student_class.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.studentClass.id = :toId where s.id = :studentId")
    void updateStudentClass(Long studentId, Long toId);
}
