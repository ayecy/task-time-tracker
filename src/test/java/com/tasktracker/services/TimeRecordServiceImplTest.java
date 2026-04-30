package com.tasktracker.service;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tasktracker.mapper.TaskMapper;
import com.tasktracker.mapper.TimeRecordMapper;
import com.tasktracker.model.Task;
import com.tasktracker.model.TimeRecord;

@ExtendWith(MockitoExtension.class)
class TimeRecordServiceImplTest {

    @Mock
    private TimeRecordMapper timeRecordMapper;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TimeRecordServiceImpl timeRecordService;

    @Test
    void shouldCreateTimeRecordSuccessfully() {
        // Arrange
        TimeRecord record = new TimeRecord();
        record.setTaskId(1L);
        record.setStartTime(LocalDateTime.now());
        record.setEndTime(LocalDateTime.now().plusHours(2));

        Task mockTask = new Task();
        mockTask.setId(1L);
        when(taskMapper.findById(1L)).thenReturn(mockTask);

        // Act
        TimeRecord created = timeRecordService.createTimeRecord(record);

        // Assert
        assertNotNull(created);
        verify(taskMapper).findById(1L);
        verify(timeRecordMapper).insert(record);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        // Arrange
        TimeRecord record = new TimeRecord();
        record.setTaskId(999L);
        when(taskMapper.findById(999L)).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> timeRecordService.createTimeRecord(record));
    }

    @Test
    void shouldThrowExceptionWhenEndTimeBeforeStartTime() {
        // Arrange
        TimeRecord record = new TimeRecord();
        record.setTaskId(1L);
        record.setStartTime(LocalDateTime.now().plusHours(2));
        record.setEndTime(LocalDateTime.now()); // Конец раньше начала

        Task mockTask = new Task();
        mockTask.setId(1L);
        when(taskMapper.findById(1L)).thenReturn(mockTask);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> timeRecordService.createTimeRecord(record));
        verify(timeRecordMapper, never()).insert(any()); // Убедимся, что в БД не полезли
    }
}