package it.sevenbits.backend.taskmanager.core.repository;

import it.sevenbits.backend.taskmanager.core.model.Task;
import it.sevenbits.backend.taskmanager.web.model.AddTaskRequest;

import java.util.List;

/**
 * Interface for task repository
 */
public interface TaskRepository {
    /**
     * Create new task
     *
     * @param request request with data for task
     * @return created task
     */
    Task createTask(final AddTaskRequest request);

    /**
     * Create new task with some status
     *
     * @param request request with data for task
     * @param status  some status for a task
     * @return created task
     */
    Task createTask(final AddTaskRequest request, final String status);

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
    List<Task> getTasksBy(final String status);

    /**
     * Get all saved task
     *
     * @return list of a tasks
     */
    List<Task> getAllTasks();

    /**
     * Remove task from repository
     *
     * @param taskId ID of a task
     */
    void removeTask(final String taskId);
}
