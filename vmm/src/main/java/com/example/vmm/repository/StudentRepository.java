package com.example.vmm.repository;

import com.example.vmm.entity.Grade;
import com.example.vmm.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {


    @Query("""
            SELECT count(grade) 
            from Grade grade
            inner join STUDENTS student on  student.id = grade.studentId
            group by grade.studentId
            """)
    List<String> getallStudentsAndGrades();

    @Query("""
            select grade
            from Grade grade
            where grade.studentId = ?1
            """)
    List<Grade> getGradesByStudentId(String id);

    @Query("""
            select student
            from STUDENTS student
            order by student.firstName asc 
            """)
    List<Student> findAllSorted();

    @Query("""
            update STUDENTS student
            set student.grades = ?1 
            where student.id = ?2
            """)
    void updateStudent(List<Grade> grades, String studentId);
}
