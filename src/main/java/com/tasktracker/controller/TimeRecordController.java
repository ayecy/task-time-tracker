package com.tasktracker.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tasktracker.model.TimeRecord;
import com.tasktracker.service.TimeRecordService;

@RestController
@RequestMapping("/records")
public class TimeRecordController {

    private final TimeRecordService timeRecordService;

    public TimeRecordController(TimeRecordService timeRecordService) {
        this.timeRecordService = timeRecordService;
    }

    // 1. Создать запись (POST /records)
    @PostMapping
    public ResponseEntity<TimeRecord> createRecord(@RequestBody TimeRecord record) {
        TimeRecord created = timeRecordService.createTimeRecord(record);
        return ResponseEntity.created(URI.create("/records/" + created.getId())).body(created);
    }

    // 2. Получить записи за период (GET /records/period?employeeId=1&start=...&end=...)
    @GetMapping("/period")
    public ResponseEntity<List<TimeRecord>> getRecordsByPeriod(
            @RequestParam Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        
        List<TimeRecord> records = timeRecordService.getRecordsByPeriod(employeeId, start, end);
        return ResponseEntity.ok(records);
    }
}