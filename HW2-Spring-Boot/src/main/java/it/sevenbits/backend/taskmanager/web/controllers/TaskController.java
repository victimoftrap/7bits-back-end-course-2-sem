package it.sevenbits.backend.taskmanager.web.controllers;

import it.sevenbits.backend.taskmanager.core.model.Task;
import it.sevenbits.backend.taskmanager.core.repository.TaskRepository;

import it.sevenbits.backend.taskmanager.web.model.AddTaskRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(repository.getAllTasks());
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
                .build()
                .toUri();

        return ResponseEntity
                .created(location)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(task);
    }
}
