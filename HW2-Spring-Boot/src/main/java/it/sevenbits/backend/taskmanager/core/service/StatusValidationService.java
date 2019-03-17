package it.sevenbits.backend.taskmanager.core.service;

import java.util.regex.Pattern;

/**
 * Class for validation of a status
 */
public class StatusValidationService implements Verifiable<String> {
    private Pattern pattern;

    /**
     * Create validation
     */
    public StatusValidationService() {
        pattern = Pattern.compile("^inbox|done$");
    }

    @Override
    public boolean verify(String status) {
        return pattern.matcher(status).matches();
    }
}
