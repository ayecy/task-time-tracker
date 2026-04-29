package com.tasktracker.service;

import java.time.LocalDateTime;
import java.util.List;

import com.tasktracker.model.TimeRecord;

public interface TimeRecordService {
    TimeRecord createTimeRecord(TimeRecord record);
    List<TimeRecord> getRecordsByPeriod(Long employeeId, LocalDateTime start, LocalDateTime end);
}