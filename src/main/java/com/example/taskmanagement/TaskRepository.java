package com.example.taskmanagement;

import com.example.taskmanagement.models.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task,Long> {
}
