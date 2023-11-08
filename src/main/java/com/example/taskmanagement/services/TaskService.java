package com.example.taskmanagement.services;

import com.example.taskmanagement.TaskRepository;
import com.example.taskmanagement.models.Task;
import com.example.taskmanagement.payloads.Response;
import com.example.taskmanagement.payloads.requests.TaskManagingRequest;
import com.example.taskmanagement.utils.HttpStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
                .status(HttpStatus.success.statusCode)
                .msg(HttpStatus.success.message)
                .data(tasks)
                .build();
    }

    public Response<Task> createTask(TaskManagingRequest taskManagingRequest) {
        Task task = Task.builder()
                .title(taskManagingRequest.getTitle())
                .description(taskManagingRequest.getDescription())
                .completed(taskManagingRequest.getCompleted())
                .build();
        task = taskRepository.save(task);
        return Response.<Task>builder()
                .status(HttpStatus.success.statusCode)
                .msg(HttpStatus.success.message)
                .data(task)
                .build();
    }

    public Response<Task> getTaskById(long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()) {
            return Response.<Task>builder()
                    .status(HttpStatus.notFound.statusCode)
                    .msg(HttpStatus.notFound.message)
                    .build();
        }
        return Response.<Task>builder()
                .status(HttpStatus.success.statusCode)
                .msg(HttpStatus.success.message)
                .data(taskOptional.get())
                .build();
    }

    public Response<Task> updateTaskById(long id, TaskManagingRequest taskManagingRequest) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()) {
            return Response.<Task>builder()
                    .status(HttpStatus.notFound.statusCode)
                    .msg(HttpStatus.notFound.message)
                    .build();
        }
        Task task = taskOptional.get();
        task.setDescription(taskManagingRequest.getDescription());
        task.setTitle(taskManagingRequest.getTitle());
        task.setCompleted(taskManagingRequest.getCompleted());
        taskRepository.save(task);

        return Response.<Task>builder()
                .status(HttpStatus.success.statusCode)
                .msg(HttpStatus.success.message)
                .data(task)
                .build();
    }

    public Response<Task> deleteTaskById(long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()) {
            return Response.<Task>builder()
                    .status(HttpStatus.notFound.statusCode)
                    .msg(HttpStatus.notFound.message)
                    .build();
        }

        taskRepository.deleteById(id);
        return Response.<Task>builder()
                .status(HttpStatus.success.statusCode)
                .msg(HttpStatus.success.message)
                .data(taskOptional.get())
                .build();
    }
}
