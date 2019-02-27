package it.sevenbits.backend.identification.servlets;

import it.sevenbits.backend.identification.repository.SessionRepository;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
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
        response.setCharacterEncoding("UTF-8");

        String userName = request.getParameter("name");
        if (userName == null || "".equals(userName)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No user name");
            return;
        }

        String sessionId = UUID.randomUUID().toString();
        repository.addUser(sessionId, userName);

        Cookie cookie = new Cookie("sessionId", sessionId);
        response.addCookie(cookie);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_CREATED);
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
        response.setCharacterEncoding("UTF-8");

        if (request.getCookies() == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Optional<Cookie> opt = Arrays.stream(request.getCookies())
                .filter(monster -> "sessionId".equals(monster.getName()))
                .findFirst();
        if (!opt.isPresent()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        Cookie requestCookie = opt.get();

        String userName = repository.getUser(requestCookie.getValue());
        if (userName == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            return;
        }

        response.getWriter().write(
                String.format("<!DOCTYPE html><html><body><p>Current user is %s</p></body></html>", userName));
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
