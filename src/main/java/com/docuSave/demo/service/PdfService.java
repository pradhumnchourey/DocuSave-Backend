package com.docuSave.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docuSave.demo.model.PdfFile;
import com.docuSave.demo.model.User;
import com.docuSave.demo.repo.PdfRepository;
import com.docuSave.demo.repo.UserRepository;

@Service
public class PdfService {

    @Autowired
    private PdfRepository pdfRepository;

    @Autowired
    private UserRepository userRepository;

    public void savePdf(String originalFilename, String docType, String docUri, int userId) {
        PdfFile pdfFile = new PdfFile();
        pdfFile.setFileName(originalFilename);
        pdfFile.setDocUri(docUri);
        pdfFile.setDocType(docType);

        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(pdfFile::setUser);

        pdfRepository.save(pdfFile);
    }

    public PdfFile getPdfById(long pdfId) {
        return pdfRepository.findById(pdfId).orElse(null);
    }

    public List<PdfFile> getPdfByUserId(int userId) {
        return pdfRepository.findByUserId(userId);
    }

    public boolean deleteDocument(long docId) {
        if (pdfRepository.existsById(docId)) {
            pdfRepository.deleteById(docId);
            return true;
        }
        return false;
    }
}