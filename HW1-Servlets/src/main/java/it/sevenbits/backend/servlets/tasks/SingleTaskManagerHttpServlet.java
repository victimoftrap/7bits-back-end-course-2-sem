package it.sevenbits.backend.servlets.tasks;

import it.sevenbits.backend.repository.tasks.Task;
import it.sevenbits.backend.repository.tasks.TaskRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for the operations with one task
 */
public class SingleTaskManagerHttpServlet extends HttpServlet {
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
     * Find task by ID
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

        String taskId = request.getParameter("taskId");
        if (taskId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No task ID");
            return;
        }

        Task task = repository.getTask(taskId);
        if (task == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Task not found");
            return;
        }

        response.getWriter().write(String.format("{\n" +
                "  \"id\": \"%s\",\n" +
                "  \"name\": \"%s\"\n" +
                "}", taskId, task.getName()));
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Delete task by ID
     *
     * @param request  request object
     * @param response response object
     * @throws ServletException if some troubles happen
     * @throws IOException      if some troubles with writer happen
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        String sessionId = request.getHeader("Authorization");
        if (sessionId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized in service");
            return;
        }

        String taskId = request.getParameter("taskId");
        if (taskId == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Task not found");
            return;
        }

        Task task = repository.removeTask(taskId);
        if (task == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Task not found");
            return;
        }

        response.getWriter().write(String.format("{  \"id\": \"%s\"\n}", taskId));
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
