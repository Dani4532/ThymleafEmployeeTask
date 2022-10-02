package com.example.thymleafemployeetask.controller;

import com.example.thymleafemployeetask.entity.Employee;
import com.example.thymleafemployeetask.entity.Task;
import com.example.thymleafemployeetask.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/api")
public record EmployeeController(EmployeeRepository repository) {


    @GetMapping("/employees/{id}")
    public String findEmployeeById(@PathVariable String id, Model model){
        model.addAttribute("employee", repository.findById(id).get());
        return "detailPage";
    }

    @GetMapping("/employees")
    public String getAllThatContain(@RequestParam(required = false, name = "name") String name, Model model){
        if(name == null){
            model.addAttribute("employees", repository.findAll());
            return "getAllEmployees";
        }

        model.addAttribute("employees", repository.findAll()
                .stream()
                .filter(employee -> employee.getFirstName().contains(name) || employee.getLastName().contains(name))
                .toList());
        return "getAllEmployees";
    }

    @GetMapping("/tasks")
    public String getTasksByEmployee(@RequestParam(name = "id") Employee id, Model model){
        var employee = repository.findById(id.getId()).get();
        var name = employee.getFirstName() + ' ' + employee.getLastName();

        model.addAttribute("employee", name);
        model.addAttribute("tasks", repository.findTasksByEmployeeId(id.getId()));
        return "detailPage";
    }



    @GetMapping("/employees/{id}/hoursWorked")
    public String getHouresWorked(@PathVariable String id, Model model){
        model.addAttribute(repository.hoursWorkedByEmployee(id));
        return "detailPage";
    }

    @GetMapping("/employees/{id}/tasks")
    public List<Task> getTasksFromTo(@PathVariable String id, @RequestParam(name = "from") String from, @RequestParam(name = "to") String to){
        return repository.tasksFinishedByEmployee(id, LocalDate.parse(from), LocalDate.parse(to));
    }




    @PostMapping("/employees")
    ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee){
        Employee saved = repository.save(employee);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .build(saved.getId());
        return ResponseEntity
                .created(uri)
                .body(saved);
    }


}
