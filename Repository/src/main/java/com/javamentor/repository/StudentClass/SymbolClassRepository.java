package com.javamentor.repository.StudentClass;

import com.javamentor.model.student_class.SymbolClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SymbolClassRepository extends JpaRepository<SymbolClass, Long> {
    SymbolClass getSymbolClassBySymbol(Character symbol);
}
