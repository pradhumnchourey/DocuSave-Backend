package com.docuSave.demo.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.docuSave.demo.model.PdfFile;
import com.docuSave.demo.service.PdfService;

@RestController
// @RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(@RequestParam("doc") MultipartFile file, @RequestParam("docType") String docType, @RequestParam("docName") String docName, @RequestParam("userId") int userId) {

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload");
        }
        try {
            byte[] content = file.getBytes();
            pdfService.savePdf(docName, content, docType, userId);

            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }
    }

    @GetMapping("/download/{pdfId}")
    public ResponseEntity<ByteArrayResource> downloadPdf(@PathVariable long pdfId) {
        PdfFile pdfFile = pdfService.getPdfById(pdfId);

        if (pdfFile == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        httpHeaders.setContentDispositionFormData("attachment", pdfFile.getFileName());

        ByteArrayResource resource = new ByteArrayResource(pdfFile.getContent());

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentLength(pdfFile.getContent().length)
                .body(resource);
    }

}
