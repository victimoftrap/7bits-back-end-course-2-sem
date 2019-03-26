package it.sevenbits.backend.taskmanager.core.repository;

import it.sevenbits.backend.taskmanager.core.model.Task;
import org.springframework.jdbc.core.JdbcOperations;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of task repository based on connection to database
 */
public class DatabaseTaskRepository implements TaskRepository {
    private DateFormat dateFormat;
    private String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private JdbcOperations jdbcOperations;

    /**
     * Create repository
     *
     * @param jdbcOperations class that works with database
     */
    public DatabaseTaskRepository(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
        this.dateFormat = new SimpleDateFormat(DATE_FORMAT);
    }

    @Override
    public Task createTask(final String text, final String status) {
        String id = UUID.randomUUID().toString();
        String createdAt = dateFormat.format(new Date());
//        jdbcOperations.update(
//                "INSERT INTO tasks VALUES(?,?,?,?,?)",
//                id, text, status, createdAt, createdAt
//        );
//        return new Task(id, text, status, createdAt, createdAt);
        jdbcOperations.update(
                "INSERT INTO tasks VALUES(?,?,?,?)",
                id, text, status, createdAt
        );
        return new Task(id, text, status, createdAt);
    }

    @Override
    public Task getTask(final String taskId) {
//        return jdbcOperations.queryForObject(
//                "SELECT id, text, status, createdAt, updatedAt FROM tasks WHERE id=?",
//                (resultSet, id) -> {
//                    String text = resultSet.getString(2);
//                    String status1 = resultSet.getString(3);
//                    String createdAt = resultSet.getString(4);
//                    String updatedAt = resultSet.getString(5);
//                    return new Task(taskId, text, status1, createdAt, updatedAt);
//                },
//                taskId
//        );
        return jdbcOperations.queryForObject(
                "SELECT id, text, status, createdAt FROM tasks WHERE id=?",
                (resultSet, id) -> {
                    String text = resultSet.getString(2);
                    String status1 = resultSet.getString(3);
                    String createdAt = resultSet.getString(4);
                    return new Task(taskId, text, status1, createdAt);
                },
                taskId
        );
    }

    @Override
    public List<Task> getTasks(final String status) {
//        return jdbcOperations.query(
//                "SELECT id, text, status, createdAt, updatedAt FROM tasks",
//                (resultSet, i) -> {
//                    String taskId = resultSet.getString(1);
//                    String text = resultSet.getString(2);
//                    String status1 = resultSet.getString(3);
//                    String createdAt = resultSet.getString(4);
//                    String updatedAt = resultSet.getString(5);
//                    return new Task(taskId, text, status1, createdAt, updatedAt);
//                }
//        );
        return jdbcOperations.query(
                "SELECT id, text, status, createdAt FROM tasks",
                (resultSet, i) -> {
                    String taskId = resultSet.getString(1);
                    String text = resultSet.getString(2);
                    String status1 = resultSet.getString(3);
                    String createdAt = resultSet.getString(4);
                    return new Task(taskId, text, status1, createdAt);
                }
        );
    }

    @Override
    public Task removeTask(final String taskId) {
        throw new UnsupportedOperationException("Not ready yet");
    }

    @Override
    public void updateTask(final String taskId, final Task updated) {
        String updText = updated.getText();
        String updStatus = updated.getStatus();
//        String updTime = dateFormat.format(new Date());
//        jdbcOperations.update(
//                "UPDATE tasks SET text=?, status=?, updatedAt=? WHERE id=?",
//                updText, updStatus, updTime, taskId
//        );
        jdbcOperations.update(
                "UPDATE tasks SET text=?, status=? WHERE id=?",
                updText, updStatus, taskId
        );
    }
}
