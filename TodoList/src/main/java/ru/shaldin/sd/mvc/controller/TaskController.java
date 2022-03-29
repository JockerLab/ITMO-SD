package ru.shaldin.sd.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.shaldin.sd.mvc.dao.TaskDao;
import ru.shaldin.sd.mvc.dao.TodoListDao;
import ru.shaldin.sd.mvc.model.Task;
import ru.shaldin.sd.mvc.model.TodoList;

@Controller
public class TaskController {
    private final TaskDao taskDao;
    private final TodoListDao todoListDao;

    public TaskController(TaskDao taskDao, TodoListDao todoListDao) {
        this.taskDao = taskDao;
        this.todoListDao = todoListDao;
    }

    @RequestMapping(value = "/add-task", method = RequestMethod.POST)
    public String addTask(@ModelAttribute("task") Task task, @RequestParam int todoListId) {
        taskDao.addTask(task);
        todoListDao.addTask(todoListId, task);
        return "redirect:/get-todo-lists";
    }

    @RequestMapping(value = "/update-task", method = RequestMethod.POST)
    public String markTask(@RequestParam int taskId, @RequestParam boolean done) {
        taskDao.updateTask(taskId, done);
        return "redirect:/get-todo-lists";
    }
}
