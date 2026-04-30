package com.tasktracker.service;

import com.tasktracker.mapper.TaskMapper;         
import com.tasktracker.mapper.TimeRecordMapper;
import com.tasktracker.model.Task;               
import com.tasktracker.model.TimeRecord;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TimeRecordServiceImpl implements TimeRecordService {

    private final TimeRecordMapper timeRecordMapper;
    private final TaskMapper taskMapper;          

    // Конструктор с двумя параметрами
    public TimeRecordServiceImpl(
            TimeRecordMapper timeRecordMapper, 
            TaskMapper taskMapper) {
        this.timeRecordMapper = timeRecordMapper;
        this.taskMapper = taskMapper;
    }

    @Override
    public TimeRecord createTimeRecord(TimeRecord record) {
        // ПРОВЕРКА 1: Существует ли задача?
        Task task = taskMapper.findById(record.getTaskId());
        if (task == null) {
            // Кидаем исключение с понятным сообщением
            throw new RuntimeException("Задача с ID " + record.getTaskId() + " не найдена");
        }
        
        // ПРОВЕРКА 2: Логика времени
        if (record.getEndTime() != null && record.getStartTime().isAfter(record.getEndTime())) {
            throw new IllegalArgumentException("Время окончания не может быть раньше времени начала");
        }
        
        // Если всё ок — сохраняем в базу
        timeRecordMapper.insert(record);
        return record;
    }

    @Override
    public List<TimeRecord> getRecordsByPeriod(Long employeeId, LocalDateTime start, LocalDateTime end) {
        return timeRecordMapper.findByEmployeeAndPeriod(employeeId, start, end);
    }
}