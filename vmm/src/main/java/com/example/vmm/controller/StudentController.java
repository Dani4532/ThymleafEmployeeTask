package com.example.vmm.controller;

import com.example.vmm.loader.GradeLoader;
import com.example.vmm.repository.GradeRepository;
import com.example.vmm.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class StudentController{

    final private StudentRepository studentRepository;
    final private GradeRepository gradeRepository;
    final private GradeLoader gradeLoader;

    public StudentController(StudentRepository studentRepository, GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
        this.gradeLoader= new GradeLoader(gradeRepository, studentRepository);

    }


    @GetMapping("/students")
    public String getAllStudents(Model model) throws IOException, InterruptedException {
        if (studentRepository.findAll().get(1).getGrades().isEmpty()) {
            gradeLoader.loadGrades();
        }
        model.addAttribute("content", studentRepository.findAllSorted());
        return "students";
    }

    @GetMapping("/detail/{id}")
    public String getDetailtoStudent(@PathVariable String id, Model model){
        model.addAttribute("student", studentRepository.findById(id).get());
        return "detailPage";
    }

}
