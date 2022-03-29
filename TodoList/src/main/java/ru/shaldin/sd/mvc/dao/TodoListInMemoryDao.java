package ru.shaldin.sd.mvc.dao;

import ru.shaldin.sd.mvc.model.Task;
import ru.shaldin.sd.mvc.model.TodoList;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TodoListInMemoryDao implements TodoListDao {
    private final AtomicInteger lastId = new AtomicInteger(0);
    private final List<TodoList> todoLists = new CopyOnWriteArrayList<>();

    @Override
    public int addTodoList(TodoList todoList) {
        int id = lastId.incrementAndGet();
        todoList.setId(id);
        todoList.setTasks(new ArrayList<>());
        todoLists.add(todoList);
        return id;
    }

    @Override
    public List<TodoList> getTodoLists() {
        return List.copyOf(todoLists);
    }

    @Override
    public void addTask(int todoListId, Task task) {
        todoLists.stream().filter(list -> list.getId() == todoListId).findFirst().ifPresent(list -> list.addTask(task));
    }

    @Override
    public List<Task> getTasks(int todoListId) {
        return List.copyOf(todoLists.stream().filter(list -> list.getId() == todoListId).findFirst().map(TodoList::getTasks).get());
    }

    @Override
    public void deleteTodoList(int todoListId) {
        todoLists.stream().filter(list -> list.getId() == todoListId).findFirst().ifPresent(todoLists::remove);
    }
}
