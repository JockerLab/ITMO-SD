package ru.shaldin.sd.mvc.dao;

import ru.shaldin.sd.mvc.model.Task;
import ru.shaldin.sd.mvc.model.TodoList;

import java.util.List;

public interface TodoListDao {
    int addTodoList(TodoList todoList);

    void deleteTodoList(int id);

    List<Task> getTasks(int id);

    List<TodoList> getTodoLists();

    void addTask(int id, Task task);
}
