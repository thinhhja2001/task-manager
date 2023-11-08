package com.example.taskmanagement.services;

import com.example.taskmanagement.repositories.TaskRepository;
import com.example.taskmanagement.models.Task;
import com.example.taskmanagement.payloads.responses.Response;
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
        try {
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
        } catch (Exception e) {
            System.out.println(e);
            return Response.<List<Task>>builder()
                    .status(HttpStatus.internalServerError.statusCode)
                    .msg(HttpStatus.internalServerError.message)
                    .build();
        }

    }

    public Response<Task> createTask(TaskManagingRequest taskManagingRequest) {
        try {
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
        } catch (Exception e) {
            System.out.println(e);
            return Response.<Task>builder()
                    .status(HttpStatus.internalServerError.statusCode)
                    .msg(HttpStatus.internalServerError.message)
                    .build();
        }

    }

    public Response<Task> getTaskById(long id) {
        try {
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
        } catch (Exception e) {
            return Response.<Task>builder()
                    .status(HttpStatus.internalServerError.statusCode)
                    .msg(HttpStatus.internalServerError.message)
                    .build();
        }

    }

    public Response<Task> updateTaskById(long id, TaskManagingRequest taskManagingRequest) {
        try {
            Optional<Task> taskOptional = taskRepository.findById(id);
            if (taskOptional.isEmpty()) {
                return Response.<Task>builder()
                        .status(HttpStatus.notFound.statusCode)
                        .msg(HttpStatus.notFound.message)
                        .build();
            }
            Task task = taskOptional.get();
            task.setDescription(taskManagingRequest.getDescription() == null ? task.getDescription() : taskManagingRequest.getDescription());
            task.setTitle(taskManagingRequest.getTitle() == null ? task.getTitle() : taskManagingRequest.getTitle());
            task.setCompleted(taskManagingRequest.getCompleted() == null ? task.getCompleted() : taskManagingRequest.getCompleted());
            Task savedTask = taskRepository.save(task);

            return Response.<Task>builder()
                    .status(HttpStatus.success.statusCode)
                    .msg(HttpStatus.success.message)
                    .data(savedTask)
                    .build();
        } catch (Exception e) {
            System.out.println(e);
            return Response.<Task>builder()
                    .status(HttpStatus.internalServerError.statusCode)
                    .msg(HttpStatus.internalServerError.message)
                    .build();
        }

    }

    public Response<Task> deleteTaskById(long id) {
        try {
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
        } catch (Exception e) {
            System.out.println(e);
            return Response.<Task>builder()
                    .status(HttpStatus.internalServerError.statusCode)
                    .msg(HttpStatus.internalServerError.message)
                    .build();
        }
    }
}
