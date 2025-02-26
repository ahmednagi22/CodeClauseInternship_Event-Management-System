package org.EventManagement.models;

import java.sql.Time;

public class Schedule {
    private int id;
    private int eventId;
    private String activity;
    private Time startTime;
    private Time endTime;

    public Schedule(Integer eventId, String activity, Time startTime, Time endTime) {
        this. eventId = eventId;
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public Schedule(int id, Integer eventId, String activity, Time startTime, Time endTime) {
        this.id = id;
        this.eventId = eventId;
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
