package com.mismirnov.todolistproject.controller;

import com.mismirnov.todolistproject.entity.Task;
import com.mismirnov.todolistproject.entity.User;
import com.mismirnov.todolistproject.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class TaskController {

    private final TaskService service;

    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public String getAllTasks(Model model) {
        List<Task> list = service.getAllTasks();

        model.addAttribute("tasks", list);
        return "tasklist";
    }

    @GetMapping(path = {"/edit", "/edit/{id}"})
    public String editTaskById(Model model, @PathVariable("id") Optional<Integer> id)
            throws Exception {
        if (id.isPresent()) {
            Task task = service.getTaskById(id.get());
            model.addAttribute("task", task);
        } else {
            model.addAttribute("task", new Task());
        }
        return "edittask";
    }

    @GetMapping(path = "/delete/{id}")
    public String deleteTaskById(@PathVariable("id") Integer id)
            throws Exception {
        service.deleteTaskById(id);
        return "redirect:/";
    }

    @PostMapping(path = "/createTask")
    public String createOrUpdateTask(@AuthenticationPrincipal User user, @Valid Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edittask";
        }
        service.createOrUpdateTask(task, user);
        return "redirect:/";
    }
}


