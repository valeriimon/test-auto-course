package ua.edu.duan.test_course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.duan.test_course.entity.DocumentEntity;

import java.util.List;

public interface DocumentRepository extends JpaRepository<DocumentEntity, String> {
}
