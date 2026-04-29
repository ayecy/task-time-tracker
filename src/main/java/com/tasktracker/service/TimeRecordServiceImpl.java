package com.tasktracker.service;

import java.time.LocalDateTime;        // 1. Импортируй TaskMapper
import java.util.List;

import org.springframework.stereotype.Service;               // 2. Импортируй Task (для проверки)

import com.tasktracker.mapper.TaskMapper;
import com.tasktracker.mapper.TimeRecordMapper;
import com.tasktracker.model.Task;
import com.tasktracker.model.TimeRecord;

@Service
public class TimeRecordServiceImpl implements TimeRecordService {

    private final TimeRecordMapper timeRecordMapper;
    private final TaskMapper taskMapper;         


    public TimeRecordServiceImpl(
            TimeRecordMapper timeRecordMapper, 
            TaskMapper taskMapper) {              
        this.timeRecordMapper = timeRecordMapper;
        this.taskMapper = taskMapper;            
    }

    @Override
    public TimeRecord createTimeRecord(TimeRecord record) {
        
        // Проверка: существует ли задача
        Task task = taskMapper.findById(record.getTaskId());
        if (task == null) {
            throw new RuntimeException("Задача с ID " + record.getTaskId() + " не найдена");
        }
        
        // Проверка логики времени
        if (record.getEndTime() != null && record.getStartTime().isAfter(record.getEndTime())) {
            throw new IllegalArgumentException("Время окончания не может быть раньше времени начала");
        }
        
        timeRecordMapper.insert(record);
        return record;
    }

    @Override
    public List<TimeRecord> getRecordsByPeriod(Long employeeId, LocalDateTime start, LocalDateTime end) {
        return timeRecordMapper.findByEmployeeAndPeriod(employeeId, start, end);
    }
}