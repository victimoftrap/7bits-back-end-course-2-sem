package it.sevenbits.backend.springboot.core.repository;

import it.sevenbits.backend.springboot.core.model.Task;

import java.util.*;

/**
 * Basic implementation of TaskRepository
 */
public class BaseTaskRepository implements TaskRepository {
    private List<Task> tasks;

    /**
     * Create repository
     */
    public BaseTaskRepository() {
        tasks = new ArrayList<>();
    }

    @Override
    public Task create(Task request) {
        Task newTask = new Task(UUID.randomUUID().toString(), request.getText());
        tasks.add(newTask);
        return newTask;
    }

    @Override
    public List<Task> getAllTasks() {
        return Collections.unmodifiableList(tasks);
    }
}
