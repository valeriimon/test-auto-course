package ua.edu.duan.test_course.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import ua.edu.duan.test_course.entity.DocumentType;
import ua.edu.duan.test_course.entity.StudentEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class DocumentDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DocumentType type;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String body;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StudentEntity currentUser;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime dateSigned;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime dateCreated;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;
}
