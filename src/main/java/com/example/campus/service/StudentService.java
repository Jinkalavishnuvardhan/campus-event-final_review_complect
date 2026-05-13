package com.example.campus.service;

import com.example.campus.entity.Student;
import com.example.campus.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student login(String email, String password) {
        return studentRepository.findFirstByEmailAndPassword(email, password);
    }

    public void register(Student student) {
        studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public void updateStudent(Student student) {
        studentRepository.save(student);
    }

    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
}
