package com.tasktracker.service;

import com.tasktracker.model.Task;

public interface TaskService {
    
    Task createTask(Task task);
    
    Task getTaskById(Long id);

    void updateTaskStatus(Long id, String status);
}