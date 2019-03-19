package it.sevenbits.backend.taskmanager.core.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for validation of a status
 */
public class StatusValidationService implements Verifiable<String> {
    private List<String> systemStatus;

    /**
     * Create validation
     */
    public StatusValidationService() {
        systemStatus = new ArrayList<>();
        Collections.addAll(systemStatus, "inbox", "done");
    }

    @Override
    public boolean verify(final String status) {
        return systemStatus.contains(status);
    }
}
