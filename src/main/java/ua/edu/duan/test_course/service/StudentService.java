package ua.edu.duan.test_course.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import ua.edu.duan.test_course.dto.StudentDto;
import ua.edu.duan.test_course.entity.StudentEntity;
import ua.edu.duan.test_course.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentDto getStudent(String id) {
        return studentRepository
                .findById(id)
                .map(this::convert)
                .orElse(notFoundResponse());
    }

    public StudentDto createStudent(StudentDto dto) {
        StudentEntity entity = convert(dto);
        studentRepository.save(entity);
        return convert(entity);
    }

    @Transactional
    public StudentDto updateStudent(String id, StudentDto dto) {
        return studentRepository.findById(id)
                .map(entity -> {
                    entity.setFirstName(dto.getFirstName());
                    entity.setLastName(dto.getLastName());

                    return convert(entity);
                })
                .orElse(notFoundResponse());
    }

    @Transactional
    public String deleteStudent(String id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return "Student has been deleted";
        }

        return "Not found";
    }

    private StudentEntity convert(StudentDto dto) {
        StudentEntity entity = new StudentEntity();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        return entity;
    }

    private StudentDto convert(StudentEntity entity) {
        StudentDto dto = new StudentDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        return dto;
    }

    private StudentDto notFoundResponse() {
        StudentDto dto = new StudentDto();
        dto.setError("Student not found");
        return dto;
    }

    public String doSomething() {
        return "Hello world! Service";
    }
}
