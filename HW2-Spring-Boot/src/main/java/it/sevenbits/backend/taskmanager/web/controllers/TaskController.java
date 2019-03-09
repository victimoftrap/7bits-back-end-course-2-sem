package it.sevenbits.backend.taskmanager.web.controllers;

import it.sevenbits.backend.taskmanager.core.model.Task;
import it.sevenbits.backend.taskmanager.core.repository.TaskRepository;

import it.sevenbits.backend.taskmanager.web.model.AddTaskRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * Class-mediator that would return data from repository to user
 */
@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository repository;

    /**
     * Create controller by some repository
     *
     * @param repository repository for tasks
     */
    public TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    /**
     * Get all user's tasks
     *
     * @return list with tasks
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Task>> getTasksByStatus(@RequestParam("status") final String status) {
        if (status == null || "".equals(status)) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .build();
        }
        if (!"inbox".equals(status) && !"done".equals(status)) {
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .build();
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(repository.getTasks(status));
    }

    /**
     * Create new task
     *
     * @param request request object with text for task
     * @return created task
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Task> createTask(@RequestBody final AddTaskRequest request) {
        if (request == null || request.getText() == null || "".equals(request.getText())) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        Task task = repository.createTask(request);
        URI location = UriComponentsBuilder
                .fromPath("/tasks/")
                .path(String.valueOf(task.getId()))
                .queryParam("id", task.getId())
                .build()
                .toUri();

        return ResponseEntity
                .created(location)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(task);
    }

    /**
     * Check that ID is valid
     *
     * @param id requested ID
     * @return true if ID valid
     */
    private boolean isIdValid(final String id) {
        if (id == null) {
            return false;
        }
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * Get task by his ID
     *
     * @param id ID of a task
     * @return requested task
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Task> getTaskById(@PathVariable("id") final String id) {
        if (!isIdValid(id)) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .build();
        }

        Task task = repository.getTask(id);
        if (task == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(task);
    }

    /**
     * Update task
     *
     * @param id      ID of a task
     * @param request task with updated values
     * @return response code of operation
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Void> updateTask(@PathVariable("id") final String id, @RequestBody final Task request) {
        if (!isIdValid(id)) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        Task task = repository.getTask(id);
        if (task == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        if (request.getText() == null || "".equals(request.getText()) ||
                request.getStatus() == null || "".equals(request.getStatus())) {
            return  ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .build();
        }
        if (!"inbox".equals(request.getStatus()) && !"done".equals(request.getStatus())) {
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .build();
        }

        Task updatedTask = new Task(id, request.getText(), request.getStatus());
        repository.updateTask(id, updatedTask);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .build();
    }

    /**
     * Delete task
     *
     * @param id ID of a task
     * @return response code of operation
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteTask(@PathVariable("id") final String id) {
        if (!isIdValid(id)) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .build();
        }

        Task removed = repository.removeTask(id);
        if (removed == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .build();
    }
}
