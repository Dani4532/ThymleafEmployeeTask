package com.example.vmm.loader;

import com.example.vmm.entity.Grade;
import com.example.vmm.repository.GradeRepository;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;

public record GradeLoader(GradeRepository repository) {

    public void loadGrades() throws IOException {
        var in = Files.lines(Path.of("C:\\Users\\danie\\OneDrive\\Schuljahr22-23\\Programieren\\thymeleaf-basics\\thymleafEmployeeTask\\vmm\\src\\main\\resources\\grades.csv"));
        var grades = in.toList();

        var newGrades = grades.subList(1, grades.size());
        newGrades.forEach(grade -> {
            var array = grade.split(",");
            repository.save(new Grade(null, array[0], array[2], LocalDate.parse(array[1]), Integer.parseInt(array[3])));
        });
    }
}
