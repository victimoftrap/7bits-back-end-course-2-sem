package it.sevenbits.backend.springboot.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Class for user's task
 */
public class Task {
    private String id;
    private String text;

    /**
     * Create task
     *
     * @param id   ID of a task
     * @param text description of a task
     */
    @JsonCreator
    public Task(@JsonProperty("id") String id, @JsonProperty("text") String text) {
        this.id = id;
        this.text = text;
    }

    /**
     * Get ID of a task
     *
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * Get user description for this task
     *
     * @return text user description
     */
    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(text, task.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }
}
