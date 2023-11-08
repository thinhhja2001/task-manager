package com.example.taskmanagement.services;

import com.example.taskmanagement.TaskRepository;
import com.example.taskmanagement.models.Task;
import com.example.taskmanagement.payloads.Response;
import com.example.taskmanagement.payloads.requests.CreateTaskRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Response<List<Task>> getTasks() {
        var iterableTasks = taskRepository.findAll();
        List<Task> tasks = new ArrayList<>();
        for (Task task :
                iterableTasks) {
            tasks.add(task);
        }
        return Response.<List<Task>>builder()
                .status(200)
                .msg("Success")
                .data(tasks)
                .build();
    }

    public Response<Task> createTask(CreateTaskRequest createTaskRequest) {
        Task task = Task.builder()
                .title(createTaskRequest.getTitle())
                .description(createTaskRequest.getDescription())
                .completed(createTaskRequest.getCompleted())
                .build();
        task = taskRepository.save(task);
        return Response.<Task>builder()
                .status(200)
                .msg("Success")
                .data(task)
                .build();
    }

    public Response<Task> getTaskById(long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()) {
            return Response.<Task>builder()
                    .status(503)
                    .msg("Document not found")
                    .build();
        }
        return Response.<Task>builder()
                .status(200)
                .msg("Success")
                .data(taskOptional.get())
                .build();
    }
}
