package it.sevenbits.backend.tasks.repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class-singleton for task collection
 */
public class TaskRepository implements Iterable<Task> {
    private static TaskRepository instance;
    private ConcurrentMap<String, Task> tasks;

    /**
     * Create instance of the repository
     */
    private TaskRepository() {
        tasks = new ConcurrentHashMap<>();
    }

    /**
     * Get task repository
     *
     * @return repository instance
     */
    public static TaskRepository getInstance() {
        if (instance == null) {
            instance = new TaskRepository();
        }
        return instance;
    }

    /**
     * Add new task in repository
     *
     * @param task new task
     */
    public void addTask(Task task) {
        tasks.putIfAbsent(task.getId(), task);
    }

    /**
     * Get task by its id
     *
     * @param id task id
     * @return task
     */
    public Task getTask(String id) {
        return tasks.get(id);
    }

    /**
     * Remove task by its id
     *
     * @param id task id
     * @return removed task
     */
    public Task removeTask(String id) {
        return tasks.remove(id);
    }

    /**
     * Get iterator for current stage of repository
     *
     * @return task iterator
     */
    @Override
    public Iterator<Task> iterator() {
        Map<String, Task> snapshot = new HashMap<>();
        for (String id : tasks.keySet()) {
            snapshot.put(id, tasks.get(id));
        }
        return snapshot.values().iterator();
    }
}
