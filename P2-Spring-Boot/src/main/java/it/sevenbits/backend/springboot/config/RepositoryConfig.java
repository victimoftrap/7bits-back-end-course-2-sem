package it.sevenbits.backend.springboot.config;

import it.sevenbits.backend.springboot.core.repository.BaseTaskRepository;
import it.sevenbits.backend.springboot.core.repository.TaskRepository;
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
