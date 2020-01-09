package com.mismirnov.todolistproject.repository;

import com.mismirnov.todolistproject.entity.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {

}


