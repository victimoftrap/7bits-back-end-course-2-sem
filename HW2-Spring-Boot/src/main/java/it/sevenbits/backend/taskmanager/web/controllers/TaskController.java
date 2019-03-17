package it.sevenbits.backend.taskmanager.web.controllers;

import it.sevenbits.backend.taskmanager.core.model.Task;
import it.sevenbits.backend.taskmanager.core.repository.TaskRepository;
import it.sevenbits.backend.taskmanager.core.service.IdValidationService;
import it.sevenbits.backend.taskmanager.web.model.AddTaskRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
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
    private IdValidationService idValidation = new IdValidationService();

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
     * @param status status of needed tasks
     * @return list with tasks or empty list
     * * Code 200 - successful operation, all tasks returned
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Task>> getTasksByStatus(@RequestParam("status") final String status) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(repository.getTasks(status));
    }

    /**
     * Create new task
     *
     * @param request request object with text for task
     * @return status code of operation
     * * Code 201 - task created;
     * * Code 400 - request invalid or request text are empty
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> createTask(@RequestBody final AddTaskRequest request) {
        if (request == null || request.getText() == null || "".equals(request.getText())) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        Task task = repository.createTask(request.getText(), "inbox");
        URI location = UriComponentsBuilder
                .fromPath("/tasks/")
                .path(String.valueOf(task.getId()))
                .queryParam("id", task.getId())
                .build()
                .toUri();

        return ResponseEntity
                .created(location)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .build();
    }

    /**
     * Get task by his ID
     *
     * @param id ID of a task
     * @return requested task
     * * Code 200 - successful operation;
     * * Code 404 - task by ID not found
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Task> getTaskById(@PathVariable("id") final String id) {
        if (!idValidation.verify(id)) {
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
     * * Code 200 - successful operation;
     * * Code 400 - validation exception;
     * * Code 404 - task by ID not found.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<Void> updateTask(@PathVariable("id") final String id, @RequestBody final Task request) {
        if (!idValidation.verify(id)) {
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
     * * Code 200 - successful operation;
     * * Code 404 - task by ID not found.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteTask(@PathVariable("id") final String id) {
        if (!idValidation.verify(id)) {
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
