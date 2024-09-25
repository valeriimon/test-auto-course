package ua.edu.duan.test_course.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ua.edu.duan.test_course.dto.DocumentDto;
import ua.edu.duan.test_course.dto.StudentDto;
import ua.edu.duan.test_course.entity.DocumentEntity;
import ua.edu.duan.test_course.entity.StudentEntity;
import ua.edu.duan.test_course.repository.DocumentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final EntityManager entityManager;

    public DocumentService(
            DocumentRepository documentRepository,
            EntityManager entityManager
    ) {
        this.documentRepository = documentRepository;
        this.entityManager = entityManager;
    }

    public List<DocumentDto> findDocuments(
            String currentUserId,
            Boolean signed,
            String dateFrom,
            String dateTo
    ) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentEntity> query = criteriaBuilder.createQuery(DocumentEntity.class);
        Root<DocumentEntity> root = query.from(DocumentEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (currentUserId != null && currentUserId.isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("currentUserId"), currentUserId));
        }

        if (signed != null) {
            predicates.add(signed
                    ? criteriaBuilder.isNotNull(root.get("dateSigned"))
                    : criteriaBuilder.isNull(root.get("dateSigned")));
        }

        if (dateFrom != null && dateTo != null) {
            predicates.add(criteriaBuilder.between(root.get("dateCreated"), dateFrom, dateTo)) ;
        }

        if (dateFrom != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dateCreated"), dateFrom));
        }

        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList()
                .stream()
                .map(this::convert)
                .toList();
    }



    public DocumentDto createDocument(DocumentDto dto) {
        DocumentEntity entity = documentRepository.save(convert(dto));
        return convert(entity);
    }

    @Transactional
    public DocumentDto updateDocument(String id, DocumentDto dto) {
        return documentRepository.findById(id)
                .map(entity -> {
                    updateDocumentFields(entity, dto);
                    return convert(entity);
                })
                .orElse(errorResponse("Document not found"));
    }

    @Transactional
    public DocumentDto deleteDocument(String id) {
        DocumentDto dto = new DocumentDto();
        if (documentRepository.existsById(id)) {
            documentRepository.deleteById(id);
            dto.setSuccess(true);
            return dto;
        }

        dto.setSuccess(false);
        dto.setError("Document not found");
        return dto;
    }

    private DocumentEntity convert(DocumentDto dto) {
        DocumentEntity entity = new DocumentEntity();
        entity.setId(dto.getId());
        entity.setType(dto.getType());
        entity.setName(dto.getName());
        entity.setBody(dto.getBody());

        return entity;
    }

    private DocumentDto convert(DocumentEntity entity) {
        DocumentDto dto = new DocumentDto();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setName(entity.getName());
        dto.setBody(entity.getBody());
        dto.setCurrentUser(entity.getCurrentUser());
        dto.setDateSigned(entity.getDateSigned());
        dto.setDateCreated(entity.getDateCreated());

        return dto;
    }

    private void updateDocumentFields(DocumentEntity entity, DocumentDto dto) {
        Optional.ofNullable(dto.getName()).ifPresent(dto::setName);
        Optional.ofNullable(dto.getType()).ifPresent(dto::setType);
        Optional.ofNullable(dto.getBody()).ifPresent(dto::setBody);
        Optional.ofNullable(dto.getDateSigned()).ifPresent(dto::setDateSigned);
    }

    private DocumentDto errorResponse(String errorMsg) {
        DocumentDto dto = new DocumentDto();
        dto.setError(errorMsg);
        return dto;
    }
}
