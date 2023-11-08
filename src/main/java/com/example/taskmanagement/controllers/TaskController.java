package com.example.taskmanagement.controllers;

import com.example.taskmanagement.payloads.Response;
import com.example.taskmanagement.models.Task;
import com.example.taskmanagement.payloads.requests.CreateTaskRequest;
import com.example.taskmanagement.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping()
    public ResponseEntity<Response<List<Task>>> getTasks() {
        Response<List<Task>> response = taskService.getTasks();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping()
    public ResponseEntity<Response<Task>> createTask(@RequestBody CreateTaskRequest createTaskRequest) {
        Response<Task> response = taskService.createTask(createTaskRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Task>>getTaskById(@PathVariable long id){
        Response<Task>response = taskService.getTaskById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}