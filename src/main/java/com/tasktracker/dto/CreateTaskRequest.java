package com.tasktracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateTaskRequest {
    
    @NotBlank(message = "Название задачи не может быть пустым")
    @Size(min = 3, max = 50, message = "Название должно быть от 3 до 50 символов")
    private String title;
    
    private String description;
}