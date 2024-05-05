package com.docuSave.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.docuSave.demo.model.DocList;
import com.docuSave.demo.model.Docs;
import com.docuSave.demo.service.DocService;

@RestController
@CrossOrigin("http://192.168.29.43:8080")
public class DocController {

    @Autowired
    private DocService docService;

    @GetMapping("/documents/{userId}")
    public List<DocList> getDocs(@PathVariable int userId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type) {
        if (type == null || type.isEmpty() || type.equals("all")) {
            return fetchDocs(userId, category);
        } else {
            return fetchDocsByType(userId, category, type);
        }
    }

    private List<DocList> fetchDocs(int userId, String category) {
        List<Docs> pdffiles = docService.getPdfByUserIdAndCategory(userId, category);
        List<DocList> docLists = new ArrayList<>();
        for (Docs pdfFile : pdffiles) {
            docLists.add(new DocList(pdfFile.getFileId(), pdfFile.getFileName(), pdfFile.getDocType(),
                    pdfFile.getDocUri(), pdfFile.getCategory()));
        }
        return docLists;
    }

    private List<DocList> fetchDocsByType(int userId, String category, String type) {
        List<Docs> filteredDocs = docService.getPdfByUserIdAndCategoryAndType(userId, category, type);
        List<DocList> docLists = new ArrayList<>();
        for (Docs doc : filteredDocs) {
            docLists.add(new DocList(doc.getFileId(), doc.getFileName(), doc.getDocType(),
                    doc.getDocUri(), doc.getCategory()));
        }
        return docLists;
    }

    @DeleteMapping("/documents/{docId}")
    public ResponseEntity<String> deleteDocument(@PathVariable long docId) {
        boolean deleted = docService.deleteDocument(docId);
        if (deleted) {
            return ResponseEntity.ok("Document deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document not found");
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocs(@RequestBody DocList formData) {
        String docUri = formData.getDocUri();
        if (docUri == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload");
        }
        docService.savePdf(formData.getDocName(), formData.getDocType(), docUri, formData.getUserId(),
                formData.getCategory());
        return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully.");
    }
}