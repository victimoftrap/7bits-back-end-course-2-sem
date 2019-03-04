package it.sevenbits.backend.springboot.core.repository;

import it.sevenbits.backend.springboot.core.model.Task;

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
    Task create(Task request);

    /**
     * Get all saved task
     *
     * @return list of tasks
     */
    List<Task> getAllTasks();
}
