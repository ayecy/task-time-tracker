package com.tasktracker.mapper;

import com.tasktracker.model.TimeRecord;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TimeRecordMapper {
    
    @Insert("INSERT INTO time_records(employee_id, task_id, start_time, end_time, description) " +
            "VALUES(#{employeeId}, #{taskId}, #{startTime}, #{endTime}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(TimeRecord timeRecord);
    
    @Select("SELECT * FROM time_records WHERE id = #{id}")
    TimeRecord findById(Long id);
    
    @Select("SELECT * FROM time_records WHERE task_id = #{taskId}")
    List<TimeRecord> findByTaskId(Long taskId);
    
    @Select("SELECT * FROM time_records WHERE employee_id = #{employeeId} " +
            "AND start_time >= #{startDate} AND (end_time IS NULL OR end_time <= #{endDate})")
    List<TimeRecord> findByEmployeeAndPeriod(
            @Param("employeeId") Long employeeId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
    
    @Update("UPDATE time_records SET end_time = #{endTime}, description = #{description} WHERE id = #{id}")
    void update(TimeRecord timeRecord);
    
    @Delete("DELETE FROM time_records WHERE id = #{id}")
    void delete(Long id);
}