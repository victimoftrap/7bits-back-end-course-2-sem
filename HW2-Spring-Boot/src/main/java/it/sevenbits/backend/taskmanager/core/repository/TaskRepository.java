package it.sevenbits.backend.taskmanager.core.repository;

import it.sevenbits.backend.taskmanager.core.model.Task;

import java.util.List;

/**
 * Interface for task repository
 */
public interface TaskRepository {
    /**
     * Create new task with some status
     *
     * @param text   text of a task
     * @param status some status for a task
     * @return created task
     */
    Task createTask(final String text, final String status);

    /**
     * Get task by his ID
     *
     * @param taskId ID of a task
     * @return task from repository
     */
    Task getTask(final String taskId);

    /**
     * Get all tasks by some status
     *
     * @param status some task status
     * @return list of a task
     */
    List<Task> getTasks(final String status);

    /**
     * Remove task from repository
     *
     * @param taskId ID of a task
     * @return value associated with key
     */
    Task removeTask(final String taskId);

    /**
     * Update fields of a task
     *
     * @param taskId  ID of a task
     * @param updated new task
     */
    void updateTask(final String taskId, final Task updated);
}
