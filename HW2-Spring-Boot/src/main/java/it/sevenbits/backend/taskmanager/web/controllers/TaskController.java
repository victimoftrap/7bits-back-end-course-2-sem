package it.sevenbits.backend.taskmanager.web.controllers;

import it.sevenbits.backend.taskmanager.core.model.Task;
import it.sevenbits.backend.taskmanager.core.repository.TaskRepository;

import it.sevenbits.backend.taskmanager.web.model.AddTaskRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    /**
     * Get all user's tasks
     *
     * @return list with tasks
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Task>> getTasksByStatus(@RequestParam("status") String status) {
        // TODO check if status are 'inbox' or 'done'
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
    public ResponseEntity<Task> createTask(@RequestBody AddTaskRequest request) {
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
     * Get task by his ID
     *
     * @param id ID of a task
     * @return requested task
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Task> getTaskById(@PathVariable("id") String id) {
        // TODO check if ID isn't UUID

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
    public ResponseEntity<Void> updateTask(@PathVariable("id") String id, @RequestBody Task request) {
        // TODO check if ID isn't UUID

        Task task = repository.getTask(id);
        if (task == null) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .build();
        }

        // TODO check fields of update request
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
    public ResponseEntity<Void> deleteTask(@PathVariable("id") String id) {
        // TODO check if ID isn't UUID

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
