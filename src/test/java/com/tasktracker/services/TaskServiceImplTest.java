package com.tasktracker.service;

// === JUnit 5 ===
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tasktracker.exception.TaskNotFoundException;
import com.tasktracker.mapper.TaskMapper;
import com.tasktracker.model.Task;
import com.tasktracker.model.TaskStatus;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void shouldCreateTaskSuccessfully() {
        // Arrange
        Task task = new Task();
        task.setTitle("Тестовая задача");
        task.setStatus(TaskStatus.NEW);

        // Act
        Task createdTask = taskService.createTask(task);

        // Assert
        assertNotNull(createdTask);
        verify(taskMapper).insert(task);
    }

    @Test
    void shouldReturnTaskById() {
        // Arrange
        Long taskId = 1L;
        Task mockTask = new Task();
        mockTask.setId(taskId);
        mockTask.setTitle("Задача");
        when(taskMapper.findById(taskId)).thenReturn(mockTask);

        // Act
        Task foundTask = taskService.getTaskById(taskId);

        // Assert
        assertEquals(taskId, foundTask.getId());
        verify(taskMapper).findById(taskId);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        // Arrange
        Long taskId = 999L;
        when(taskMapper.findById(taskId)).thenReturn(null);

        // Act & Assert
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(taskId));
    }

    @Test
    void shouldUpdateStatusSuccessfully() {
        // Arrange
        Long taskId = 1L;
        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setStatus(TaskStatus.NEW);
        when(taskMapper.findById(taskId)).thenReturn(existingTask);

        // Act
        taskService.updateTaskStatus(taskId, "IN_PROGRESS");

        // Assert
        assertEquals(TaskStatus.IN_PROGRESS, existingTask.getStatus());
        verify(taskMapper).update(existingTask);
    }

    @Test
    void shouldThrowExceptionOnInvalidStatus() {
        // Arrange
        Long taskId = 1L;
        Task existingTask = new Task();
        existingTask.setId(taskId);
        when(taskMapper.findById(taskId)).thenReturn(existingTask);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> taskService.updateTaskStatus(taskId, "INVALID_STATUS"));
    }
} 