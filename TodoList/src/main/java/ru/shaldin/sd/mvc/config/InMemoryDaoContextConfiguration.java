package ru.shaldin.sd.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.shaldin.sd.mvc.dao.TaskDao;
import ru.shaldin.sd.mvc.dao.TodoListDao;
import ru.shaldin.sd.mvc.dao.TaskInMemoryDao;
import ru.shaldin.sd.mvc.dao.TodoListInMemoryDao;

@Configuration
public class InMemoryDaoContextConfiguration {
    @Bean
    public TaskDao taskDao() {
        return new TaskInMemoryDao();
    }

    @Bean
    public TodoListDao todoListDao() {
        return new TodoListInMemoryDao();
    }
}