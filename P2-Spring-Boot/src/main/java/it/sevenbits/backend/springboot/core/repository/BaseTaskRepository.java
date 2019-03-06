package it.sevenbits.backend.springboot.core.repository;

import it.sevenbits.backend.springboot.core.model.Task;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Basic implementation of TaskRepository
 */
public class BaseTaskRepository implements TaskRepository {
    private ConcurrentMap<String, Task> tasks;

    /**
     * Create repository
     */
    public BaseTaskRepository() {
        tasks = new ConcurrentHashMap<>();
    }

    @Override
    public Task create(Task request) {
        Task newTask = new Task(UUID.randomUUID().toString(), request.getText());
        tasks.putIfAbsent(newTask.getId(), newTask);
        return newTask;
    }

    @Override
    public List<Task> getAllTasks() {
        return Collections.unmodifiableList(new ArrayList<>(tasks.values()));
    }
}
