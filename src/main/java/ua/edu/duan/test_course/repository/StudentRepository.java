package ua.edu.duan.test_course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.edu.duan.test_course.entity.DocumentEntity;
import ua.edu.duan.test_course.entity.StudentEntity;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity, String> {
}
