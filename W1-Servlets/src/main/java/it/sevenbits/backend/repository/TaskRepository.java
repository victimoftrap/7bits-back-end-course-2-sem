package it.sevenbits.backend.repository;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TaskRepository {
    private static TaskRepository instance;
    private ConcurrentMap<String, String> tasks;

    private TaskRepository() {
        tasks = new ConcurrentHashMap<>();
    }

    public static TaskRepository getInstance() {
        if (instance == null) {
            instance = new TaskRepository();
        }
        return instance;
    }

    public void addTask(String id, String task) {
        tasks.putIfAbsent(id, task);
    }

    public String getTask(String id) {
        return tasks.get(id);
    }
}
