package com.tasktracker.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.tasktracker.model.Task;

@Mapper
public interface TaskMapper {
    
    @Insert("INSERT INTO tasks(title, description, status) VALUES(#{title}, #{description}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Task task);
    
    @Select("SELECT * FROM tasks WHERE id = #{id}")
    Task findById(Long id);
    
    @Select("SELECT * FROM tasks")
    List<Task> findAll();
    
    @Update("UPDATE tasks SET title = #{title}, description = #{description}, status = #{status} WHERE id = #{id}")
    void update(Task task);
    
    @Delete("DELETE FROM tasks WHERE id = #{id}")
    void delete(Long id);
}