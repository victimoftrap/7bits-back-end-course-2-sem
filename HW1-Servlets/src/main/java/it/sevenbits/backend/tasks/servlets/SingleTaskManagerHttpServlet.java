package it.sevenbits.backend.tasks.servlets;

import it.sevenbits.backend.tasks.repository.Task;
import it.sevenbits.backend.tasks.repository.TaskRepository;

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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String sessionId = request.getHeader("Authorization");
        if (sessionId == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
            return;
        }

        String taskId = request.getParameter("taskId");
        Task task = repository.getTask(taskId);
        if (task == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Task not found");
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(String.format("{\n" +
                "  \"id\": \"%s\",\n" +
                "  \"name\": \"%s\"\n" +
                "}", taskId, task.getName()));
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
        response.setContentType("application/json");
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

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(String.format("{  \"id\": \"%s\"\n}", taskId));
    }
}