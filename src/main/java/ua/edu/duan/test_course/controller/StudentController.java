package ua.edu.duan.test_course.controller;

import org.springframework.web.bind.annotation.*;
import ua.edu.duan.test_course.dto.StudentDto;
import ua.edu.duan.test_course.entity.StudentEntity;
import ua.edu.duan.test_course.service.StudentService;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String doSomething() {
        return studentService.doSomething();
    }

    @GetMapping(path = "/student")
    public StudentDto getStudent(@RequestParam String id) {
        return studentService.getStudent(id);
    }

    @PostMapping(path = "/student")
    public StudentDto createStudent(@RequestBody StudentDto body) {
        return studentService.createStudent(body);
    }

    @PutMapping(path = "/student")
    public StudentDto updateStudent(@RequestParam String id, @RequestBody StudentDto body) {
        return studentService.updateStudent(id, body);
    }

    @DeleteMapping(path = "/student")
    public String deleteStudent(@RequestParam String id) {
        return studentService.deleteStudent(id);
    }
}
