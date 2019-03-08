package it.sevenbits.backend.taskmanager.core.repository;

import it.sevenbits.backend.taskmanager.core.model.Task;
import it.sevenbits.backend.taskmanager.web.model.AddTaskRequest;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
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
    private String DEFAULT_STATUS = "inbox";

    /**
     * Create repository
     */
    public BaseTaskRepository() {
        tasks = new ConcurrentHashMap<>();
    }

    @Override
    public Task createTask(final AddTaskRequest request) {
        return this.createTask(request, DEFAULT_STATUS);
    }

    @Override
    public Task createTask(final AddTaskRequest request, String status) {
        Task newTask = new Task(UUID.randomUUID().toString(), request.getText(), status);
        tasks.putIfAbsent(newTask.getId(), newTask);
        return newTask;
    }

    public Task getTask(final String taskId) {
        return tasks.get(taskId);
    }

    @Override
    public List<Task> getTasks(final String status) {
        Map<String, Task> snapshot = new HashMap<>();
        for (String taskId : tasks.keySet()) {
            snapshot.put(taskId, tasks.get(taskId));
        }

        List<Task> result = new ArrayList<>();
        for (String taskId : snapshot.keySet()) {
            Task task = snapshot.get(taskId);
            if (task.getStatus().equals(status)) {
                result.add(task);
            }
        }
        return Collections.unmodifiableList(result);
    }

    @Override
    public List<Task> getAllTasks() {
        return Collections.unmodifiableList(new ArrayList<>(tasks.values()));
    }

    @Override
    public Task removeTask(final String taskId) {
        return tasks.remove(taskId);
    }

    @Override
    public void updateTask(String taskId, Task updated) {
        tasks.putIfAbsent(taskId, updated);
    }
}
