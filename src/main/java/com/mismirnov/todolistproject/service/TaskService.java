package com.mismirnov.todolistproject.service;

import com.mismirnov.todolistproject.entity.Task;
import com.mismirnov.todolistproject.entity.User;
import com.mismirnov.todolistproject.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jca.cci.RecordTypeNotSupportedException;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository repository;

    @Autowired
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getAllTasks() {
        List<Task> result = (List<Task>) repository.findAll();
        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Task>();
        }
    }

    public Task getTaskById(Integer id) throws Exception {
        Optional<Task> task = repository.findById(id);
        if (task.isPresent()) {
            return task.get();
        } else {
            throw new Exception();
        }
    }

    public void createOrUpdateTask(Task entity, User user) {
        if (entity.getId() == null) {
            entity.setUser(user);
            entity.setStatus(false);
            repository.save(entity);
        } else {
            Task finalEntity = entity;
            repository.findById(entity.getId()).map(newTask -> {
                newTask.setName(finalEntity.getName());
                newTask.setDescription(finalEntity.getDescription());
                newTask.setStatus(finalEntity.isStatus());
                newTask.setPriority(finalEntity.getPriority());
                newTask.setDate(finalEntity.getDate());
                newTask = repository.save(newTask);
                return newTask;
            }).orElse(repository.save(entity));
        }
    }

    public void deleteTaskById(Integer id) throws Exception {
        Optional<Task> task = repository.findById(id);
        if (task.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new Exception("No employee record exist for given id");
        }
    }
}