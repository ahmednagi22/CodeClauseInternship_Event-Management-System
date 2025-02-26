package org.EventManagement.controller;

import org.EventManagement.database.ScheduleRepository;
import org.EventManagement.models.Attendee;
import org.EventManagement.models.Schedule;

import java.util.List;

public class ScheduleController {
    ScheduleRepository scheduleRepository;
    public ScheduleController(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }
    public boolean addSchedule(Schedule schedule){
        return scheduleRepository.addSchedule(schedule);
    }
    public boolean updateSchedule(Schedule schedule){
        return scheduleRepository.updateSchedule(schedule);
    }
    public boolean deleteSchedule(int id){
        return scheduleRepository.deleteSchedule(id);
    }
    public Schedule getScheduleById(int id){
        return scheduleRepository.getScheduleById(id);
    }
    public List<Schedule> getAllSchedules(){
       return scheduleRepository.getAllSchedules();
    }
}
