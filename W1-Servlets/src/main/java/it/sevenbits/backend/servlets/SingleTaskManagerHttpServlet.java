package it.sevenbits.backend.servlets;

import com.google.gson.Gson;
import it.sevenbits.backend.repository.Task;
import it.sevenbits.backend.repository.TaskRepository;

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
    private Gson gson;

    /**
     * Get instance of task repository
     *
     * @throws ServletException if some troubles happen
     */
    @Override
    public void init() throws ServletException {
        super.init();
        repository = TaskRepository.getInstance();
        gson = new Gson();
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

        response.setContentType("application/json");
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
        response.setCharacterEncoding("UTF-8");

        String taskId = request.getParameter("taskId");
        if (taskId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No task ID");
            return;
        }

        Task task = repository.removeTask(taskId);
        if (task == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Task not found");
            return;
        }

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(String.format("{  \"id\": \"%s\"\n}", taskId));
    }
}
