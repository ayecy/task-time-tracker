package com.tasktracker.service;

import org.springframework.stereotype.Service;

import com.tasktracker.exception.TaskNotFoundException;
import com.tasktracker.mapper.TaskMapper;
import com.tasktracker.model.Task;
import com.tasktracker.model.TaskStatus;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper; // Ссылка на Mapper

    // Конструктор для Внедрения Зависимостей (Dependency Injection)
    // Spring увидит этот конструктор и сам подставит сюда TaskMapper
    public TaskServiceImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public Task createTask(Task task) {
        // Вызываем Mapper. 
        taskMapper.insert(task);
        
        return task; // Возвращаем заполненную задачу
    }

    @Override
    public Task getTaskById(Long id) {
        // Ищем в базе
        Task task = taskMapper.findById(id);
        
        // ПРОВЕРКА НА NULL
        // Если в базе ничего нет, task будет null
        if (task == null) {
            throw new TaskNotFoundException(id);
        }
        
        return task;
    }

    @Override
    public void updateTaskStatus(Long id, String status) {
        Task task = getTaskById(id);
        TaskStatus newStatus = TaskStatus.valueOf(status.toUpperCase());
        task.setStatus(newStatus);
        taskMapper.update(task);
    }
}