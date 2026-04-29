package com.tasktracker.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tasktracker.dto.CreateTaskRequest;
import com.tasktracker.model.Task;
import com.tasktracker.model.TaskStatus;
import com.tasktracker.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks") // Базовый путь для всех методов в этом классе
@RequiredArgsConstructor 
public class TaskController {

    private final TaskService taskService;

    // 1. Создание задачи (POST /tasks)
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody CreateTaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        
        task.setStatus(TaskStatus.NEW); 
        
        Task createdTask = taskService.createTask(task);
        
        return ResponseEntity.created(URI.create("/tasks/" + createdTask.getId())).body(createdTask);
    }

    // 2. Получение задачи по ID (GET /tasks/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    // 3. Изменение статуса (PATCH /tasks/{id}/status)
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestParam String status) {
        taskService.updateTaskStatus(id, status);
        return ResponseEntity.ok().build();
    }
}