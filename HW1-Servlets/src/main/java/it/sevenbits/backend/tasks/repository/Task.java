package it.sevenbits.backend.tasks.repository;

import java.util.Objects;

/**
 * Class-model for task
 */
public class Task {
    private String id;
    private String name;

    /**
     * Create task
     *
     * @param id   id of the task
     * @param name name of the task
     */
    public Task(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Get task id
     *
     * @return id of the task
     */
    public String getId() {
        return id;
    }

    /**
     * Get task name
     *
     * @return name of the task
     */
    public String getName() {
        return name;
    }

    /**
     * Reset task name
     *
     * @param name new task name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(name, task.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
