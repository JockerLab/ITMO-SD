package ru.shaldin.sd.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.shaldin.sd.mvc.dao.TaskDao;
import ru.shaldin.sd.mvc.dao.TodoListDao;
import ru.shaldin.sd.mvc.model.Task;
import ru.shaldin.sd.mvc.model.TodoList;

import java.util.List;

@Controller
public class TodoListController {
    private final TodoListDao todoListDao;
    private final TaskDao taskDao;

    public TodoListController(TodoListDao todoListDao, TaskDao taskDao) {
        this.todoListDao = todoListDao;
        this.taskDao = taskDao;
    }

    @RequestMapping(value = "/add-todo-list", method = RequestMethod.POST)
    public String addTodoList(@ModelAttribute("todoList") TodoList todoList) {
        todoListDao.addTodoList(todoList);
        return "redirect:/get-todo-lists";
    }

    @RequestMapping(value = "/delete-todo-list", method = RequestMethod.POST)
    public String deleteTodoList(@RequestParam int todoListId) {
        todoListDao.deleteTodoList(todoListId);
        return "redirect:/get-todo-lists";
    }

    @RequestMapping(value = "/get-todo-lists", method = RequestMethod.GET)
    public String getTodoLists(ModelMap map) {
        prepareModelMap(map, todoListDao.getTodoLists());
        return "index";
    }

    private void prepareModelMap(ModelMap map, List<TodoList> todoLists) {
        map.addAttribute("todoLists", todoLists);
        map.addAttribute("todoList", new TodoList());
        map.addAttribute("task", new Task());
    }
}
