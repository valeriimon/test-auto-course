package ua.edu.duan.test_course.controller;

import org.springframework.web.bind.annotation.*;
import ua.edu.duan.test_course.dto.DocumentDto;
import ua.edu.duan.test_course.service.DocumentService;

import java.util.List;

@RestController
@RequestMapping("api/v1/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    public List<DocumentDto> getUserDocuments(
            @RequestParam(required = false) String currentUserId,
            @RequestParam(required = false) Boolean signed,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
        return documentService.findDocuments(currentUserId, signed, from, to);
    }

    @PostMapping
    public DocumentDto createDocument(@RequestBody DocumentDto body) {
        return documentService.createDocument(body);
    }

    @PutMapping(path = "{id}")
    public DocumentDto updateDocument(@PathVariable String id, @RequestBody DocumentDto body) {
        return documentService.updateDocument(id, body);
    }
}
