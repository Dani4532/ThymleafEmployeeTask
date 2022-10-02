package com.example.vmm.controller;

import com.example.vmm.loader.GradeLoader;
import com.example.vmm.loader.NameAndGrade;
import com.example.vmm.repository.GradeRepository;
import com.example.vmm.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController{

    final private StudentRepository studentRepository;
    final private GradeRepository gradeRepository;

    public StudentController(StudentRepository studentRepository, GradeRepository gradeRepository) throws IOException {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
        var grLoader = new GradeLoader(gradeRepository);
        grLoader.loadGrades();
    }


    @GetMapping("/students")
    public String getAllStudents(Model model) {

        var studentList = studentRepository.findAllSorted();
        var gradeList = studentRepository.getallStudentsAndGrades();
        List<NameAndGrade> nameGradeList = new ArrayList<>(List.of());
        for (int i = 0; i < studentList.size(); i++){
            var student = studentList.get(i);
            if (gradeList.size() == i){
                nameGradeList.add(new NameAndGrade(student.getId(), student.getFirstName(), student.getLastName(), "0"));
            }else{
                var grade = gradeList.get(i);
                nameGradeList.add(new NameAndGrade(student.getId(), student.getFirstName(), student.getLastName(), grade));
            }
        }
        model.addAttribute("content", nameGradeList);
        return "students";
    }

    @GetMapping("/detail/{id}")
    public String getDetailtoStudent(@PathVariable String id, Model model){
        model.addAttribute("student", studentRepository.findById(id).get());
        model.addAttribute("grades", studentRepository.getGradesByStudentId(id));
        return "detailPage";
    }

}
