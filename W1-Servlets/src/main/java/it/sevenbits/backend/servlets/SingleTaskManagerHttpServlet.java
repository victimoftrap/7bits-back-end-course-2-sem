package it.sevenbits.backend.servlets;

import it.sevenbits.backend.repository.TaskRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SingleTaskManagerHttpServlet extends HttpServlet {
    private TaskRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = TaskRepository.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String taskId = request.getParameter("taskId");
        String task = repository.getTask(taskId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(String.format("{" +
                                                    "  \"id\": %s," +
                                                    "  \"name\": %s" +
                                                    "}", taskId, task));
    }
}
