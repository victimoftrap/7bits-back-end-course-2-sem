package it.sevenbits.backend.taskmanager.core.repository;

import it.sevenbits.backend.taskmanager.core.model.Task;

import java.util.*;

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
        List<Task> result = new ArrayList<>();
        for (Map.Entry<String, Task> stringTaskEntry : tasks.entrySet()) {
            Task task = stringTaskEntry.getValue();
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
