package ru.shaldin.sd.mvc.dao;

import ru.shaldin.sd.mvc.model.Task;
import ru.shaldin.sd.mvc.model.TodoList;

import java.util.List;

public interface TaskDao {
    int addTask(Task task);

    void updateTask(int id, boolean value);
}
