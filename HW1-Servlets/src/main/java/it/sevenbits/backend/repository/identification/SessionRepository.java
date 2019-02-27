package it.sevenbits.backend.repository.identification;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Class that would collect users session
 */
public class SessionRepository {
    private static SessionRepository instance;
    private ConcurrentMap<String, String> sessions;

    /**
     * Create instance of the repository
     */
    private SessionRepository() {
        sessions = new ConcurrentHashMap<>();
    }

    /**
     * Get instance of session repository
     *
     * @return repository instance
     */
    public static SessionRepository getInstance() {
        if (instance == null) {
            instance = new SessionRepository();
        }
        return instance;
    }

    /**
     * Add new user session
     *
     * @param sessionId ID of session
     * @param userName  name of the user
     */
    public void addUser(String sessionId, String userName) {
        sessions.putIfAbsent(sessionId, userName);
    }

    /**
     * Get user name
     *
     * @param sessionId ID ot session
     * @return user name
     */
    public String getUser(String sessionId) {
        return sessions.get(sessionId);
    }
}
