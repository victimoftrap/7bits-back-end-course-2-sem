package it.sevenbits.backend.springboot.web.controllers;

import it.sevenbits.backend.springboot.core.model.Task;
import it.sevenbits.backend.springboot.core.repository.TaskRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Class-mediator that would return data from repository to user
 */
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
    public List<Task> getAllTasks() {
        return repository.getAllTasks();
    }
}
