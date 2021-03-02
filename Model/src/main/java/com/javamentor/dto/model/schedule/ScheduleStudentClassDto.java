package com.javamentor.dto.model.schedule;

import java.util.List;

public class ScheduleStudentClassDto {

    private List<ScheduleStudentClassOfDayDto> studentClassOfDayDtoList;

    public ScheduleStudentClassDto(List<ScheduleStudentClassOfDayDto> studentClassOfDayDtoList) {
        this.studentClassOfDayDtoList = studentClassOfDayDtoList;
    }

    public List<ScheduleStudentClassOfDayDto> getStudentClassOfDayDtoList() {
        return studentClassOfDayDtoList;
    }

    public void setStudentClassOfDayDtoList(List<ScheduleStudentClassOfDayDto> studentClassOfDayDtoList) {
        this.studentClassOfDayDtoList = studentClassOfDayDtoList;
    }

}
