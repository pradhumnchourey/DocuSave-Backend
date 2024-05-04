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
import org.springframework.web.bind.annotation.RestController;

import com.docuSave.demo.model.DocList;
import com.docuSave.demo.model.PdfFile;
import com.docuSave.demo.service.PdfService;

@RestController
@CrossOrigin("http://192.168.29.43:8080")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/documents/{userId}")
    public List<DocList> getPdfDocs(@PathVariable int userId) {
        List<PdfFile> pdffiles = pdfService.getPdfByUserId(userId);
        List<DocList> docLists = new ArrayList<>();
        for (PdfFile pdfFile : pdffiles) {
            docLists.add(new DocList(pdfFile.getFileId(), pdfFile.getFileName(), pdfFile.getDocType(), pdfFile.getDocUri()));
        }
        return docLists;
    }

    @DeleteMapping("/documents/{docId}")
    public ResponseEntity<String> deleteDocument(@PathVariable long docId) {
        boolean deleted = pdfService.deleteDocument(docId);
        if (deleted) {
            return ResponseEntity.ok("Document deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document not found");
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(@RequestBody DocList formData){
        String docUri = formData.getDocUri();
        if (docUri==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload");
        }
        pdfService.savePdf(formData.getDocName(), formData.getDocType(), docUri, formData.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully.");
    }
}