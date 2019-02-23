package it.sevenbits.backend.servlets;

import it.sevenbits.backend.repository.SessionRepository;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Servlet for user identification
 */
public class IdentificationServlet extends HttpServlet {
    private SessionRepository repository;

    /**
     * Get instance of task repository
     *
     * @throws ServletException if some troubles happen
     */
    @Override
    public void init() throws ServletException {
        super.init();
        repository = SessionRepository.getInstance();
    }

    /**
     * Add new user
     *
     * @param request  request with user name
     * @param response response with session ID by cookie
     * @throws ServletException if some troubles happen
     * @throws IOException      if some troubles with writer happen
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_CREATED);

        String sessionId = UUID.randomUUID().toString();
        String userName = request.getParameter("name");
        repository.addUser(sessionId, userName);

        Cookie cookie = new Cookie("sessionId", sessionId);
        response.addCookie(cookie);
    }

    /**
     * Get user name
     *
     * @param request  request with session ID by cookie
     * @param response response with html-page
     * @throws ServletException if some troubles happen
     * @throws IOException      if some troubles with writer happen
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Cookie requestCookie = request.getCookies()[0];
        if (!"sessionId".equals(requestCookie.getName())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String userName = repository.getUser(requestCookie.getValue());
        if (userName == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(
                String.format("<!DOCTYPE html><html><body><p>Current user is %s</p></body></html>", userName));
    }
}
