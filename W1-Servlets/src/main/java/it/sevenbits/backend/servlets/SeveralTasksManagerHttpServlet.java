package it.sevenbits.backend.servlets;

import it.sevenbits.backend.repository.TaskRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class SeveralTasksManagerHttpServlet extends HttpServlet {
    private TaskRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = TaskRepository.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String task = request.getParameter("name");
        String taskId = UUID.randomUUID().toString();
        repository.addTask(taskId, task);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().write(
                String.format("{" +
                "  \"id\": %s," +
                "  \"name\": %s" +
                "}", taskId, task));
        response.setHeader("id", taskId);
    }
}
