package com.javamentor.dto.model.schedule;

import com.javamentor.model.direction.Lesson;

import java.time.LocalDate;
import java.util.List;

public class ScheduleStudentClassOfDayDto {

    private LocalDate scheduleStudentDate;

    private List<Lesson> lessonList;

    public ScheduleStudentClassOfDayDto(LocalDate scheduleStudentDate, List<Lesson> lessonList) {
        this.scheduleStudentDate = scheduleStudentDate;
        this.lessonList = lessonList;
    }

    public LocalDate getScheduleStudentDate() {
        return scheduleStudentDate;
    }

    public void setScheduleStudentDate(LocalDate scheduleStudentDate) {
        this.scheduleStudentDate = scheduleStudentDate;
    }

    public List<Lesson> getLessonList() {
        return lessonList;
    }

    public void setLessonList(List<Lesson> lessonList) {
        this.lessonList = lessonList;
    }

}
