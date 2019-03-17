package it.sevenbits.backend.taskmanager.core.service;

import java.util.regex.Pattern;

/**
 * Class for ID validation
 */
public class IdValidationService implements Verifiable<String> {
    private final Pattern pattern;

    /**
     * Create validator
     */
    public IdValidationService() {
        pattern = Pattern.compile("^([a-f0-9]{8}(-[a-f0-9]{4}){3}-[a-f0-9]{11})$");
    }

    @Override
    public boolean verify(final String param) {
        return pattern.matcher(param).matches();
    }
}
