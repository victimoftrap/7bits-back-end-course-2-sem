package it.sevenbits.backend.tasks.servlets;

import it.sevenbits.backend.tasks.repository.Task;
import it.sevenbits.backend.tasks.repository.TaskRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

/**
 * Servlet for the operations with all collected tasks
 */
public class SeveralTasksManagerHttpServlet extends HttpServlet {
    private TaskRepository repository;

    /**
     * Get instance of task repository
     *
     * @throws ServletException if some troubles happen
     */
    @Override
    public void init() throws ServletException {
        super.init();
        repository = TaskRepository.getInstance();
    }

    /**
     * Add a new task
     *
     * @param request  request object
     * @param response response object
     * @throws ServletException if some troubles happen
     * @throws IOException      if some troubles with writer happen
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        String sessionId = request.getHeader("Authorization");
        if (sessionId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized in service");
            return;
        }

        String taskName = request.getParameter("name");
        if (taskName == null || "".equals(taskName)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No task name");
            return;
        }

        String taskId = UUID.randomUUID().toString();
        Task task = new Task(taskId, taskName);
        repository.addTask(task);
        response.getWriter().write(String.format("{\n" +
                "  \"id\": \"%s\",\n" +
                "  \"name\": \"%s\"\n" +
                "}", taskId, task.getName()));

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader("id", taskId);
    }

    /**
     * Get tasks list
     *
     * @param request  request object
     * @param response response object
     * @throws ServletException if some troubles happen
     * @throws IOException      if some troubles with writer happen
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        String sessionId = request.getHeader("Authorization");
        if (sessionId == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
            return;
        }

        Iterator<Task> iterator = repository.iterator();
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        while (iterator.hasNext()) {
            Task task = iterator.next();
            json.append("  {\n");
            json.append(String.format("    \"id\": \"%s\",\n", task.getId()));
            json.append(String.format("    \"name\": \"%s\"\n", task.getName()));

            if (iterator.hasNext()) {
                json.append("  },\n");
            } else {
                json.append("  }\n");
            }
        }
        json.append("]");
        response.getWriter().write(json.toString());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
