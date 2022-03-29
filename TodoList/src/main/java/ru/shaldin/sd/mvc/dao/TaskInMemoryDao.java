package ru.shaldin.sd.mvc.dao;

import ru.shaldin.sd.mvc.model.Task;
import ru.shaldin.sd.mvc.model.TodoList;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskInMemoryDao implements TaskDao {
    private final AtomicInteger lastId = new AtomicInteger(0);
    private final List<Task> tasks = new CopyOnWriteArrayList<>();

    @Override
    public int addTask(Task task) {
        int id = lastId.incrementAndGet();
        task.setId(id);
        task.setDone(false);
        tasks.add(task);
        return id;
    }

    @Override
    public void updateTask(int id, boolean value) {
        tasks.stream().filter(t -> t.getId() == id).findFirst().ifPresent(t -> t.setDone(value));
    }
}
