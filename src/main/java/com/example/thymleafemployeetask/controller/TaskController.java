package com.example.thymleafemployeetask.controller;

import com.example.thymleafemployeetask.entity.Task;
import com.example.thymleafemployeetask.repository.TaskRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/task")
    public List<Task> all(){
        return repository.findAll();
    }

}
