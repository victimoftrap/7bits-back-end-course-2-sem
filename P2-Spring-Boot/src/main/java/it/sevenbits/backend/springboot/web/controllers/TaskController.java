package it.sevenbits.backend.springboot.web.controllers;

import it.sevenbits.backend.springboot.core.model.Task;
import it.sevenbits.backend.springboot.core.repository.TaskRepository;
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
    // @GetMapping
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(repository.getAllTasks());
    }

    /**
     * Create new task
     *
     * @param request request object with text for task
     * @return created task
     */
    // @PostMapping
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Task> createTask(@RequestBody Task request) {
        if (request == null || request.getText() == null || "".equals(request.getText())) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        Task task = repository.create(request);
        URI location = UriComponentsBuilder
                .fromPath("/tasks/")
                .path(String.valueOf(task.getId()))
                .build()
                .toUri();

        return ResponseEntity
                .created(location)
                .body(task);
    }
}
