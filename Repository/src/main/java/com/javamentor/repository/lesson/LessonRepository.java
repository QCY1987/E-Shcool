package com.javamentor.repository.lesson;

import com.javamentor.model.direction.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("SELECT l from Lesson l inner join l.studentClass sc where sc.id = :id AND l.startLesson BETWEEN :dayStart AND :dayFinish ")
    List<Lesson> getLessonsByStudentClassIdOfDay(Long id, LocalDateTime dayStart, LocalDateTime dayFinish);

}
