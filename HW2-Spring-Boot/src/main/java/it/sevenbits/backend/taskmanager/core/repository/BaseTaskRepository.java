package it.sevenbits.backend.taskmanager.core.repository;

import it.sevenbits.backend.taskmanager.core.model.Task;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Basic implementation of TaskRepository
 */
public class BaseTaskRepository implements TaskRepository {
    private Map<String, Task> tasks;

    /**
     * Create repository
     *
     * @param tasksContainer some map container for tasks
     */
    public BaseTaskRepository(final Map<String, Task> tasksContainer) {
        tasks = tasksContainer;
    }

    @Override
    public Task createTask(final String text, final String status) {
        Task newTask = new Task(UUID.randomUUID().toString(), text, status);
        tasks.putIfAbsent(newTask.getId(), newTask);
        return newTask;
    }

    @Override
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
    public Task removeTask(final String taskId) {
        return tasks.remove(taskId);
    }

    @Override
    public void updateTask(final String taskId, final Task updated) {
        tasks.replace(taskId, updated);
    }
}
