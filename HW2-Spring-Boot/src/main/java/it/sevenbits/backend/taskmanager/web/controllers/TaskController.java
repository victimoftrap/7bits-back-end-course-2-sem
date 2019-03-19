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

import javax.validation.Valid;
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
    public ResponseEntity<List<Task>> getTasksByStatus(
            @RequestParam(
                    value = "status",
                    required = false,
                    defaultValue = "inbox"
            ) final String status) {

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
    @RequestMapping(
            method = RequestMethod.POST,
            headers = "content-type=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ResponseBody
    public ResponseEntity<Void> createTask(@RequestBody @Valid final AddTaskRequest request) {
        Task task = repository.createTask(request.getText(), "inbox");
        URI location = UriComponentsBuilder
                .fromPath("/tasks/")
                .path(task.getId())
                .build()
                .toUri();

        return ResponseEntity
                .created(location)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .build();
    }
}
