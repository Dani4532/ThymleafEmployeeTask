package com.example.thymleafemployeetask.repository;

import com.example.thymleafemployeetask.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Integer> {


}
