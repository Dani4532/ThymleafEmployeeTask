package com.example.vmm;

import com.example.vmm.loader.GradeLoader;
import com.example.vmm.repository.GradeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class VmmApplication {

    public static void main(String[] args) throws IOException {

        SpringApplication.run(VmmApplication.class, args);
    }

}
