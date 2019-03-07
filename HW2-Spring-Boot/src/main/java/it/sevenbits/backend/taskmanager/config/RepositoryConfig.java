package it.sevenbits.backend.taskmanager.config;

import it.sevenbits.backend.taskmanager.core.repository.TaskRepository;
import it.sevenbits.backend.taskmanager.core.repository.BaseTaskRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of repository for Spring
 */
@Configuration
public class RepositoryConfig {
    /**
     * Create task repository
     *
     * @return repository
     */
    @Bean
    public TaskRepository taskRepository() {
        return new BaseTaskRepository();
    }
}
