package com.javamentor.service.scheduleService;

import com.javamentor.dto.model.schedule.ScheduleStudentClassDto;
import com.javamentor.dto.model.schedule.ScheduleStudentClassOfDayDto;

import java.time.LocalDate;
import java.time.LocalDateTime;


public interface ScheduleService {

    ScheduleStudentClassDto getScheduleStudentClassByStudentClass(Long studentClassId, LocalDate day);

    ScheduleStudentClassOfDayDto getScheduleStudentClassOfDay(Long studentClassId, LocalDateTime day);

}
