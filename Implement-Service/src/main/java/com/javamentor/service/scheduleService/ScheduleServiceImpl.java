package com.javamentor.service.scheduleService;

import com.javamentor.dto.model.schedule.ScheduleStudentClassDto;
import com.javamentor.dto.model.schedule.ScheduleStudentClassOfDayDto;
import com.javamentor.repository.lesson.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final LessonRepository lessonRepository;

    @Autowired
    public ScheduleServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public ScheduleStudentClassDto getScheduleStudentClassByStudentClass(Long studentClassId,
                                                                         final LocalDate day) {

        LocalDateTime dayWeek = LocalDateTime.of(day, LocalTime.of(0, 0));
        LocalDateTime mondayDay = dayWeek.minusDays(dayWeek.getDayOfWeek().getValue() - 1);

        List<ScheduleStudentClassOfDayDto> listSchedule = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            listSchedule.add(getScheduleStudentClassOfDay(studentClassId, mondayDay.plusDays(i)));
        }
        return new ScheduleStudentClassDto(listSchedule);
    }

    @Override
    public ScheduleStudentClassOfDayDto getScheduleStudentClassOfDay(Long studentClassId, LocalDateTime day) {
        LocalDateTime dayFinish = day.plusDays(1);
        return new ScheduleStudentClassOfDayDto(day.toLocalDate(),
                lessonRepository.getLessonsByStudentClassIdOfDay(studentClassId, day, dayFinish));
    }
}
